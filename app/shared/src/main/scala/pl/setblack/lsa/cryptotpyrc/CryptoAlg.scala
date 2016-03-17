package pl.setblack.lsa.cryptotpyrc

import scala.concurrent.Future
import scala.util.Try

trait CryptoAlg[KP <: KeyPair[_,_]] {
    def generateKeys() : Future[Try[KP]]
}

trait  CryptoSigner {
  def sign( message : String) : Future[String]
}

trait CryptoVerifier {
  def verify( message : String, signature : String) : Future[Boolean]
}




