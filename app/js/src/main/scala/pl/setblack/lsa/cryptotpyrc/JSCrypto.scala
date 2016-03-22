package pl.setblack.lsa.cryptotpyrc

import pl.setblack.lsa.cryptotpyrc.rsa.js.RSACryptoAlg

class JSCrypto extends  Crypto {
  override def rsa() = new RSACryptoAlg
}

class UniCrypto extends JSCrypto {

}