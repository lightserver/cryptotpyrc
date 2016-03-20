package pl.setblack.lsa.cryptotpyrc.rsa.js

import org.scalatest.{AsyncFunSpec, Matchers, FunSpec, FunSuite}
import pl.setblack.lsa.cryptotpyrc.JSCrypto

import scala.concurrent.Future


class RSACryptoAlgTest extends AsyncFunSpec with Matchers {

  import scala.concurrent.ExecutionContext.Implicits.global

  describe("rsa crypto") {
    val rsa = new JSCrypto().rsa

    describe("generated keys") {
      val generatedKeyPair = rsa.generateKeys()
     /* it("should be not null") {
        Future {
          generatedKeyPair should not be(null)
          //assert( generatedKeyPair == null)
        }
      }*/
      describe("and especially publick key") {
        it("should be exported") {
          val exportedString: Future[String] = generatedKeyPair.flatMap(
            keyPairGeneration => {
              val publicKey = keyPairGeneration.get.getPublic
              publicKey.export
            }
          )
          exportedString.map(exported => {
            println(exported)
            exported should not be (null)
          }
          )
        }


      }

    }


  }

}
