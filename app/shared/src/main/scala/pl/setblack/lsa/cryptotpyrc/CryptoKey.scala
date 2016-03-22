package pl.setblack.lsa.cryptotpyrc

import scala.concurrent.Future

trait CryptoKey {
  def export : Future[String]
}

trait PrivateKey extends CryptoKey

trait PublicKey extends CryptoKey

case class KeyPair[PUBLIC, PRIVATE](pub : PUBLIC, priv: PRIVATE) {

}