package pl.setblack.lsa.cryptotpyrc.rsa.js


import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Success, Try}

import org.scalajs.dom.crypto._
import pl.setblack.lsa.cryptotpyrc.CryptoAlg
import pl.setblack.lsa.cryptotpyrc.rsa.{RSAPrivateKey, RSAPublicKey, RSAKeyPair}

import ExecutionContext.Implicits.global

class RSACryptoAlg extends CryptoAlg[RSAKeyPair] {

  val myRsa = RsaHashedKeyAlgorithm.`RSASSA-PKCS1-v1_5`(
    1024, new BigInteger(js.Array(0x01, 0x00, 0x01)), HashAlgorithm.`SHA-256`)

  override def generateKeys(): Future[Try[RSAKeyPair]] = {
    val future: Future[CryptoKeyPair] = GlobalCrypto.crypto.subtle.generateKey(
      algorithm = myRsa,
      extractable = true,
      keyUsages = js.Array(KeyUsage.sign, KeyUsage.verify)
    ).toFuture
      .map {
        case x => {
          println(x)
          x.asInstanceOf[CryptoKeyPair]
        }
      }

    future.map {
      case y => {
        println(y)
        val x = y.asInstanceOf[CryptoKeyPair]

         Success(RSAKeyPair(
            RSAPublicKeyJS(x.publicKey),
            RSAPrivateKeyJS(x.privateKey)))
      }
    }
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
      exported => JSON.stringify(exported)
    )
  }
}