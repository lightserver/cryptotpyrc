package pl.setblack.lsa.cryptotpyrc


import scala.concurrent.Future
import scala.util.Try

trait CryptoAlg[PUBLIC, PRIVATE] {

  def generateKeys(): Future[Try[KeyPair[PUBLIC,PRIVATE]]]
  def sign(  key : PRIVATE, message: String) : Future[String]
  def verify( key: PUBLIC, signature : String, message: String ) :Future[Boolean]

  def importPublic( jwkKey : String) : Future[PUBLIC]
  def importPrivate( pkcs : String) : Future[PRIVATE]
}

trait CryptoSigner {
  def sign(message: String): Future[String]
}

trait CryptoVerifier {
  def verify(message: String, signature: String): Future[Boolean]
}


