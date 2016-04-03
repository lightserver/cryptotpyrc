package pl.setblack.lsa.cryptotpyrc.rsa.jvm

import java.math.BigInteger
import java.security.interfaces.RSAPublicKey
import java.util
import java.util.Base64

import org.scalatest.{Matchers, AsyncFunSpec, FunSpec}
import pl.setblack.lsa.cryptotpyrc.rsa.JwkKey

class JWKLeadingZerosBugTest extends AsyncFunSpec with Matchers{
  val leadingZerosN = "APDbvfbCVXxvQFOCV2yHO4P9pZTZBG79sNo2k1PTwXWJd_T_-wCBuO14fQtTkcQRfFhZYx6wy6TEvzLUFmtNJ7X4iuRe2Jzf-hntk2i1huz93HAY67N6w0mUYtAp1S9rZdXisgAFgDeVUhjhBV1r4_KmvGRFojQwuIRymAHHF2nr"
  val bytes = Base64.getUrlDecoder.decode(leadingZerosN)
  val realModulus = new BigInteger(1, Base64.getUrlDecoder.decode(leadingZerosN))
  describe("rsa crypto") {
    val jvmKey = RSAPublicKeyJVM( new MockedRSAPubKey)
    it("should be strip leading zeros in exported jwk") {
       for {
         exportedJWK <- jvmKey.export
         decodedKey  = upickle.default.read[JwkKey](exportedJWK)
         decodedBytes = Base64.getUrlDecoder.decode(decodedKey.n)
       } yield (decodedBytes(0) should not be(0))

    }
  }

  class MockedRSAPubKey extends RSAPublicKey {


    override def getPublicExponent: BigInteger = new BigInteger("65537")

    override def getModulus: BigInteger = realModulus

    override def getEncoded: Array[Byte] = ???

    override def getFormat: String = ???

    override def getAlgorithm: String = ???
  }

}

