package pl.setblack.lsa.cryptotpyrc.rsa


import pl.setblack.lsa.cryptotpyrc.{PublicKey, PrivateKey, KeyPair}

trait RSAPrivateKey extends PrivateKey {

}

trait RSAPublicKey  extends PublicKey {

}

case class RSAKeyPair(publ : RSAPublicKey, priv : RSAPrivateKey)  extends KeyPair[RSAPublicKey, RSAPrivateKey]  {
  override def getPrivate: RSAPrivateKey = priv

  override def getPublic: RSAPublicKey = publ
}
