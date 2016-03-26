package pl.setblack.lsa.cryptotpyrc.rsa.jvm

import pl.setblack.lsa.cryptotpyrc.{UniCrypto, Crypto, UniHashBaseSpec}

class UniHashTest extends UniHashBaseSpec{
  override def createCrypto(): Crypto = new UniCrypto
}
