package pl.setblack.lsa.cryptotpyrc

import pl.setblack.lsa.cryptotpyrc.rsa.RSAKeyPair

import scala.concurrent.Promise


trait Crypto {
  def rsa() : CryptoAlg[RSAKeyPair]
}
