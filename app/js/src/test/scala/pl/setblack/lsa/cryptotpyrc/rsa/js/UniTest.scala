package pl.setblack.lsa.cryptotpyrc.rsa.js

import pl.setblack.lsa.cryptotpyrc.{UniCrypto, Crypto, UniBaseSpec}

class UniTest extends  UniBaseSpec {
  override def createCrypto(): Crypto = new UniCrypto
}
