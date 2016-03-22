package pl.setblack.lsa.cryptotpyrc

import pl.setblack.lsa.cryptotpyrc.rsa.{RSAPrivateKey, RSAPublicKey}

import scala.concurrent.Promise


trait Crypto {
  def rsa() : CryptoAlg[RSAPublicKey, RSAPrivateKey]
}
