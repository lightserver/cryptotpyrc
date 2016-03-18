package pl.setblack.lsa.cryptotpyrc

import scala.concurrent.Future

trait CryptoKey {
  def export : Future[String]
}

trait PrivateKey extends CryptoKey

trait PublicKey extends CryptoKey

trait KeyPair[PUBLIC, PRIVATE] {
  type PUB = PUBLIC
  type PRIV = PRIVATE
  def getPrivate :  PRIVATE
  def getPublic :  PUBLIC
}