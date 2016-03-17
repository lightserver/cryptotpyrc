package pl.setblack.lsa.cryptotpyrc.rsa

import pl.setblack.lsa.cryptotpyrc.{PublicKey, PrivateKey, KeyPair}

class RSAPrivateKey extends PrivateKey {
  override def export: String = ???
}

class RSAPublicKey extends PublicKey {
  override def export: String = ???
}

case class RSAKeyPair(publ : RSAPublicKey, priv : RSAPrivateKey)  extends KeyPair[RSAPublicKey, RSAPrivateKey]  {
  override def getPrivate: RSAPrivateKey = priv

  override def getPublic: RSAPublicKey = publ
}
