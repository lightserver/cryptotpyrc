package pl.setblack.lsa.cryptotpyrc.rsa.js



import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.typedarray._
import scala.util.{Success, Try}

import org.scalajs.dom.crypto._
import pl.setblack.lsa.cryptotpyrc.{KeyPair, CryptoAlg}
import pl.setblack.lsa.cryptotpyrc.rsa.{RSAPrivateKey, RSAPublicKey}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

class RSACryptoAlg extends CryptoAlg[RSAPublicKey, RSAPrivateKey] {
  type RSAKeyPair = KeyPair[RSAPublicKey, RSAPrivateKey]

  val myRsa = RsaHashedKeyAlgorithm.`RSASSA-PKCS1-v1_5`(
    1024, new BigInteger(js.Array(0x01, 0x00, 0x01)), HashAlgorithm.`SHA-256`)
  private val keyUsages =   js.Array(KeyUsage.sign, KeyUsage.verify)

  val base64coder:SBase64ArrayBuffer = SBase64ArrayBuffer.instance

  override def generateKeys(): Future[RSAKeyPair] = {
    val future: Future[CryptoKeyPair] = GlobalCrypto.crypto.subtle.generateKey(
      algorithm = myRsa,
      extractable = true,
      keyUsages
    ).toFuture
      .map {
        case x => {
          x.asInstanceOf[CryptoKeyPair]
        }
      }

    future.map {
      case y => {

        val x = y.asInstanceOf[CryptoKeyPair]
         new RSAKeyPair(
          RSAPublicKeyJS(x.publicKey),
          RSAPrivateKeyJS(x.privateKey))
      }
    }
  }

  override def sign( key: RSAPrivateKey, message: String): Future[String] = {

    import scala.scalajs.js.typedarray.charArray2Uint16Array
    val data = charArray2Uint16Array(message.toCharArray)
    val result:Future[String] = GlobalCrypto.crypto.subtle.sign(myRsa, key.asInstanceOf[RSAPrivateKeyJS].native, data.buffer).toFuture.map {
      case signed => {
        val y = signed.asInstanceOf[ArrayBuffer]
        val encoded:String = base64coder.encode(y)
        encoded
      }
    }.asInstanceOf[Future[String]]
    result
  }

  override def verify(key: RSAPublicKey, signature: String, message: String): Future[Boolean] = {
    import scala.scalajs.js.typedarray.charArray2Uint16Array
    val data = charArray2Uint16Array(message.toCharArray)
    val decodedSignature = base64coder.decode(signature)

    GlobalCrypto.crypto.subtle.verify(myRsa, key.asInstanceOf[RSAPublicKeyJS].native, decodedSignature, data.buffer).toFuture.asInstanceOf[Future[Boolean]]
  }

  override def importPublic(jwkKey: String): Future[RSAPublicKey] = {
      val jsonKey = JSON.parse(jwkKey).asInstanceOf[JsonWebKey]
      GlobalCrypto.crypto.subtle.importKey(KeyFormat.jwk, jsonKey, myRsa, true,  js.Array( KeyUsage.verify))
          .toFuture.map( nativeKey => RSAPublicKeyJS(nativeKey.asInstanceOf[CryptoKey]) )

  }

  override def importPrivate(pkcs: String): Future[RSAPrivateKey] = {
    //val buffer = Base64ArrayBuffer.decode(pkcs)
    val buffer = base64coder.decode(pkcs)
    val encodedAgain = base64coder.encode(buffer)
    println(s"before:${pkcs}" )
    println(s"after:${encodedAgain}" )
    GlobalCrypto.crypto.subtle.importKey(KeyFormat.pkcs8, buffer, myRsa, true,  js.Array( KeyUsage.sign))
      .toFuture.map( nativeKey => RSAPrivateKeyJS(nativeKey.asInstanceOf[CryptoKey]) )
  }

  def digest(message: String): Future[String] = {
    val data = charArray2Uint16Array(message.toCharArray)
    GlobalCrypto.crypto.subtle.digest(myRsa.hash, data.buffer).toFuture.map(
      digested => {
        println(s"digested...${digested}")
        val y = digested.asInstanceOf[ArrayBuffer]
        println(s"digested...y ${y}")
        println(s"digested...coded ${base64coder}")
        val encoded:String = base64coder.encode(y)
        println(s"digested...encoded ${encoded}")
        encoded
      }
    )
  }
}




case class RSAPublicKeyJS(native: CryptoKey) extends RSAPublicKey {
  override def export: Future[String] = {
    GlobalCrypto.crypto.subtle.exportKey(KeyFormat.jwk, native).toFuture.map(
      exported => JSON.stringify(exported)
    )
  }
}

case class RSAPrivateKeyJS(native: CryptoKey) extends RSAPrivateKey {
  override def export: Future[String] = {
    GlobalCrypto.crypto.subtle.exportKey(KeyFormat.pkcs8, native).toFuture.map(
      exported => {
        val buffer = exported.asInstanceOf[ArrayBuffer]
        SBase64ArrayBuffer.instance.encode(buffer)
      }
    )
  }
}