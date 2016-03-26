package pl.setblack.lsa.cryptotpyrc.rsa.jvm

import pl.setblack.lsa.cryptotpyrc.rsa.JwkKey
import pl.setblack.lsa.cryptotpyrc.{Crypto, UniBaseSpec, UniCrypto}

class UniTest extends  UniBaseSpec {
  override def createCrypto(): Crypto = new UniCrypto
}
