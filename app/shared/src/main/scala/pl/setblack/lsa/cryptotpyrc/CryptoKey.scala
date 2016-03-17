package pl.setblack.lsa.cryptotpyrc

trait CryptoKey {
  def export : String
}

trait PrivateKey extends CryptoKey

trait PublicKey extends CryptoKey

trait KeyPair[PUBLIC, PRIVATE] {
  def getPrivate :  PRIVATE
  def getPublic :  PUBLIC
}