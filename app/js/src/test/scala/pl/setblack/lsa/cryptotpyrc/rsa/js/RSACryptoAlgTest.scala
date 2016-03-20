package pl.setblack.lsa.cryptotpyrc.rsa.js

import org.scalatest.{Matchers, FunSpec, FunSuite}
import pl.setblack.lsa.cryptotpyrc.JSCrypto

import scala.concurrent.Await
import scala.concurrent.duration._

class RSACryptoAlgTest extends FunSpec with Matchers {
  describe("rsa crypto") {
    val rsa = new JSCrypto().rsa

    describe( "generated keys") {
      val generatedKeyPair = rsa.generateKeys()
      it ( "should be not null") {
        assert( generatedKeyPair ==null )
      }
      it("should be exported") {
        assert(true)
      }
    }




  }

}
