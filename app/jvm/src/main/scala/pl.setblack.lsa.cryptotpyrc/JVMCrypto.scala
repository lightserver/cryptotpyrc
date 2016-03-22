package pl.setblack.lsa.cryptotpyrc

import pl.setblack.lsa.cryptotpyrc.rsa.jvm.RSACryptoAlg

class JVMCrypto extends  Crypto {
  override def rsa() = new RSACryptoAlg
}

class UniCrypto extends JVMCrypto {

}