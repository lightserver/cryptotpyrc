package pl.setblack.lsa.cryptotpyrc.rsa.jvm

import java.math.BigInteger
import java.security.spec.{PKCS8EncodedKeySpec, RSAPublicKeySpec}
import java.security._
import java.util
import java.util.Base64

import pl.setblack.lsa.cryptotpyrc.{KeyPair, CryptoAlg}
import pl.setblack.lsa.cryptotpyrc.rsa.{JwkKey, RSAPrivateKey, RSAPublicKey}

import scala.concurrent.Future
import scala.util.{Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

class RSACryptoAlg extends CryptoAlg[RSAPublicKey, RSAPrivateKey] {

  val keyGen = KeyPairGenerator.getInstance("RSA")
  keyGen.initialize(1024)

  override def generateKeys(): Future[KeyPair[RSAPublicKey, RSAPrivateKey]] = {
    val keyPair = keyGen.generateKeyPair()
    Future {
      KeyPair(
        pub = RSAPublicKeyJVM(keyPair.getPublic.asInstanceOf[java.security.interfaces.RSAPublicKey]),
        priv = RSAPrivateKeyJVM(keyPair.getPrivate)
      )
    }
  }

  override def verify(key: RSAPublicKey, signature: String, message: String): Future[Boolean] = {
    Future {
      val signatureChecker = Signature.getInstance("SHA256withRSA")
      signatureChecker.initVerify(key.asInstanceOf[RSAPublicKeyJVM].publ)
      signatureChecker.update(makeBytesMsg(message))
      signatureChecker.verify(Base64.getDecoder.decode(signature))
    }
  }

  override def importPrivate(pkcs: String): Future[RSAPrivateKey] = {
    Future {
      RSAPrivateKeyJVM(KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder.decode(pkcs))))
    }
  }

  override def importPublic(jwkKey: String): Future[RSAPublicKey] = {
    Future {
      val jsonKey = upickle.default.read[JwkKey](jwkKey)
      val modulus = new BigInteger(1, Base64.getUrlDecoder.decode(jsonKey.n))
      val exponent = new BigInteger(1, Base64.getUrlDecoder.decode(jsonKey.e))
      RSAPublicKeyJVM(
        KeyFactory.getInstance("RSA")
          .generatePublic(new RSAPublicKeySpec(modulus, exponent))
          .asInstanceOf[java.security.interfaces.RSAPublicKey]
      )
    }
  }

  override def sign(key: RSAPrivateKey, message: String): Future[String] = {
    Future {
      val signatureChecker = Signature.getInstance("SHA256withRSA")
      signatureChecker.initSign(key.asInstanceOf[RSAPrivateKeyJVM].priv)
      signatureChecker.update(makeBytesMsg(message))
      new String(Base64.getEncoder.encode(signatureChecker.sign()))
    }
  }

  override def digest(message: String): Future[String] = {
    Future {
      val digester = MessageDigest.getInstance("SHA-256")
      new String(Base64.getEncoder.encode(digester.digest(makeBytesMsg(message))))
    }
  }

  private def makeBytesMsg(msg: String) = {
    msg.getBytes("UTF-16LE")
  }
}


case class RSAPublicKeyJVM(val publ: java.security.interfaces.RSAPublicKey) extends RSAPublicKey {
  override def export: Future[String] = {
    Future {
      val modulus = publ.getModulus
      val exponent = publ.getPublicExponent
      val jwkKey = JwkKey(
        alg = "RS256",
        ext = true,
        key_ops = Seq("verify"),
        kty = "RSA",
        n = Base64.getUrlEncoder.encodeToString(modulus.toByteArray),
        e = Base64.getUrlEncoder.encodeToString(exponent.toByteArray)
      )
      upickle.default.write(jwkKey)
    }
  }
}

case class RSAPrivateKeyJVM(val priv: PrivateKey) extends RSAPrivateKey {
  override def export: Future[String] = {
    Future {
      Base64.getEncoder.encodeToString(priv.getEncoded)
    }
  }
}
