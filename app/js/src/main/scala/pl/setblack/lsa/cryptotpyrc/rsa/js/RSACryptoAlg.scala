package pl.setblack.lsa.cryptotpyrc.rsa.js

import org.scalajs.dom.crypto.{HashAlgorithm, RsaHashedKeyAlgorithm}
import pl.setblack.lsa.cryptotpyrc.rsa.RSAKeyPair
import pl.setblack.lsa.cryptotpyrc.{KeyPair, CryptoAlg}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import org.scalajs.dom.crypto._
import scala.scalajs.js.typedarray.Uint8Array
import scala.util.Try

class RSACryptoAlg extends CryptoAlg[RSAKeyPair] {

  import ExecutionContext.Implicits.global

  val myRsa = RsaHashedKeyAlgorithm.`RSASSA-PKCS1-v1_5`(
    1024, new BigInteger(js.Array(0x01, 0x00, 0x01)), HashAlgorithm.`SHA-256`)

  override def generateKeys(): Future[Try[RSAKeyPair]] = {
    val future: Future[Try[CryptoKeyPair]] = GlobalCrypto.crypto.subtle.generateKey(
      algorithm = myRsa,
      extractable = true,
      keyUsages = js.Array(KeyUsage.sign, KeyUsage.verify)
    ).toFuture
      .map(_.asInstanceOf[Try[CryptoKeyPair]])

    future.map(tryKeyGen => tryKeyGen.map( x => RSAKeyPair(null, null)) )
  }

}
