package pl.setblack.lsa.cryptotpyrc.rsa.js

import pl.setblack.lsa.cryptotpyrc.{Crypto, UniCrypto, UniHashBaseSpec}

class UniHashTest extends UniHashBaseSpec{
  override def createCrypto(): Crypto = new UniCrypto
}
