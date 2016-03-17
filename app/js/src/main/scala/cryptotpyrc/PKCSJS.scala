package cryptotpyrc


import java.security.interfaces.RSAPrivateKey
import java.util.Base64

import org.scalajs.dom
import pl.setblack.lsa.cryptotpyrc.{KeyPair, Crypto}
import org.scalajs.dom.crypto._

import scala.concurrent.Promise

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.{JSStringOps, JSON}
import scala.scalajs.js.typedarray.{Uint8Array, Uint16Array, ArrayBuffer}
import scala.util.Try


@JSName( "base64buffer")
@js.native
object Base64ArrayBuffer extends js.Object {

  def encode( byteBuffer : ArrayBuffer) : String =  js.native

  def decode( byteBuffer : String ) : ArrayBuffer = js.native

}

class PKCSJS  {



  def generateKey(): Promise[String] = {
    val result = Promise[String]()
    import js.JSConverters._
    val bigInt = new java.math.BigInteger("314155926537", 10)
    println("BIGINT=[" + new String(bigInt.toByteArray)+"]")

    import scala.scalajs.js.Thenable.Implicits._
    import scala.concurrent.ExecutionContext.Implicits.global

    val myRsa =RsaHashedKeyAlgorithm.`RSASSA-PKCS1-v1_5`(
      1024, new BigInteger(js.Array(0x01, 0x00, 0x01)), HashAlgorithm.`SHA-256`)

    val future = GlobalCrypto.crypto.subtle.generateKey(
      algorithm = myRsa,
      extractable =  true,
      keyUsages = js.Array(KeyUsage.sign, KeyUsage.verify)
    ).asInstanceOf[js.Promise[js.Any] ].toFuture

    future.andThen{
      case x:Try[js.Any] =>{
        val pair = x.get.asInstanceOf[CryptoKeyPair]
        println(s"generated ${pair.publicKey}")
        GlobalCrypto.crypto.subtle.exportKey(KeyFormat.jwk, pair.publicKey ).toFuture onSuccess  {
          case keyFormatted => {
            val rsaKey = keyFormatted.asInstanceOf[RSAPublicKey]
            println(s"formatted ${rsaKey.n}")
            println(s"formatted ${rsaKey.e}")
            val json = JSON.stringify(rsaKey)
            println(s"JSON:${json}")
            import scala.scalajs.js.typedarray.charArray2Uint16Array
            val data = charArray2Uint16Array("I have nothing to hide".toCharArray)
            val falseData = charArray2Uint16Array("I have nuthing to hide".toCharArray)
            GlobalCrypto.crypto.subtle.sign(myRsa,pair.privateKey, data.buffer).toFuture
              .andThen {
              case message =>
                val y = message.get.asInstanceOf[ArrayBuffer]
                val z = Base64ArrayBuffer.encode(y)
                println(s" to zadne ${z}")
                GlobalCrypto.crypto.subtle.verify(myRsa, pair.publicKey, y, data.buffer ).toFuture.andThen{
                  case verifi =>
                    println(s" to jest ${verifi}")
                }

            }

            result.success( "ale success")
          }
        }
        println("teraz prywatny")
        GlobalCrypto.crypto.subtle.exportKey(KeyFormat.pkcs8, pair.privateKey ).toFuture.andThen {

          case x: Try[js.Any]  => {
            val keyFormatted = x.get.asInstanceOf[ArrayBuffer]
            println(s"priv: ${keyFormatted}")
            println(s"priv: ${keyFormatted.byteLength}")
            val w = Base64ArrayBuffer.encode(keyFormatted)

            /*val w = js.Dynamic.global.String.applyDynamic("fromCharCode")( null, new Uint8Array(keyFormatted.asInstanceOf[ArrayBuffer])).asInstanceOf[String]
            val b = dom.window.btoa(w)*/
            println(s"formatted w: ${w}")

            //println(s"formatted key ${rsaKey.getEncoded}")
          }
          case what => println(s"what: ${what}")
        }


      }
    }

    result
  }
}
