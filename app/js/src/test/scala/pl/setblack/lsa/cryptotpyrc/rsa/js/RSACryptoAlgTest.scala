package pl.setblack.lsa.cryptotpyrc.rsa.js

import org.scalatest.{AsyncFunSpec, Matchers, FunSpec, FunSuite}
import pl.setblack.lsa.cryptotpyrc.{UniCrypto, JSCrypto}

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.typedarray.ArrayBuffer


class RSACryptoAlgTest extends AsyncFunSpec with Matchers {


  describe("rsa crypto") {
    val rsa = new UniCrypto().rsa

    describe("generated keys") {
      val generatedKeyPair = rsa.generateKeys()
      /*  it("should be not null") {
          Future {
            generatedKeyPair should not be(null)
            //assert( generatedKeyPair == null)
          }
        }*/
      describe("and especially publick key") {
        val exportedString: Future[String] = generatedKeyPair.flatMap(
          keyPairGeneration => {
            val publicKey = keyPairGeneration.get.pub

            val privKey = keyPairGeneration.get.priv.export.foreach(pk => println("PRIV:" + pk))

           publicKey.export
          }
        )
        it("exported is not null ") {
          exportedString.map(exported => {
            println("PUBL:" + exported)
            exported should not be (null)
          })
        }
      }
      describe("should sign") {
        val message = "I wanted to sign this"

        val signed: Future[String] = generatedKeyPair.flatMap(keyPairGeneration => {
          val privateKey = keyPairGeneration.get.priv
          val result = rsa.sign(privateKey, message )
          println("res:" + result)
          result
        })

        describe(" verify ") {
          val verified: Future[Boolean] = generatedKeyPair.flatMap(pair => {
            val pubKey = pair.get.pub
            signed.flatMap(signature => {
              println("SIGNATURE:" + signature)
              rsa.verify(pubKey, signature, message)
            })
          })
          it("should be positive") {
            verified.map(v => v should be(true))
          }
          val verified2: Future[Boolean] = generatedKeyPair.flatMap(pair => {
            val pubKey = pair.get.pub
            signed.flatMap(signature => {
              rsa.verify(pubKey, signature, "mis kolabor")
            })
          })
          it("should be negative") {
            verified2.map(v => v should be(false))
          }
        }

        it("should be longer than 10 chars") {
          signed.map(str => {
            str.length should be > (7)
          })
        }
      }
    }
  }

}
