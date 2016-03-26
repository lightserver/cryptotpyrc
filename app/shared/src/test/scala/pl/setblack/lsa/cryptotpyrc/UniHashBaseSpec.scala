package pl.setblack.lsa.cryptotpyrc

import org.scalatest.{Matchers, AsyncFunSpec}

abstract class UniHashBaseSpec extends AsyncFunSpec with Matchers {
  def createCrypto(): Crypto = ???
  val message= "yolki polki"
   val expectedHash = "wXSe0VfXZXdjDE38pw38vhW3jsak8Bfe8x3sAvZgSbE="
  describe("hash") {
    val hasher = createCrypto().rsa()
    it("should yield given result") {
      hasher.digest(message).map(hash => {
        hash should be(expectedHash)
      }
      )
    }
  }
}
