package pl.setblack.lsa.cryptotpyrc.rsa.js

import pl.setblack.lsa.cryptotpyrc.{UniCrypto, Crypto, UniBaseTest}

class UniTest extends  UniBaseTest {
  override def createCrypto(): Crypto = new UniCrypto
}
