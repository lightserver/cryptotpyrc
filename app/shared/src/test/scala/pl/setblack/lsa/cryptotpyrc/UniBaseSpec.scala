package pl.setblack.lsa.cryptotpyrc

import org.scalatest.{Matchers, AsyncFunSpec}
import pl.setblack.lsa.cryptotpyrc.rsa.RSAPublicKey
import pl.setblack.lsa.cryptotpyrc.rsa.RSAPrivateKey

import scala.concurrent.Future

abstract class UniBaseSpec extends AsyncFunSpec with Matchers {

  def createCrypto(): Crypto = ???

  describe("rsa") {
    val rsa = createCrypto.rsa()
    val importedPub: Future[RSAPublicKey] = rsa.importPublic(SampleRSAData.publicKey)
    describe("imported key") {
      it("public should not be null") {
        importedPub.map(key => {
          key should not be (null)
        })
      }
      it("should verify positive") {
        importedPub.flatMap(key => {
          rsa.verify(key, SampleRSAData.signature, SampleRSAData.message )
        }).map( result => {
          result should be (true)
        })
      }
      it("should verify negative") {
        importedPub.flatMap(key => {
          rsa.verify(key, SampleRSAData.signature, "alternate wrong message")
        }).map( result => {
          result should be (false)
        })
      }
      val importedPriv: Future[RSAPrivateKey] = importedPub.flatMap(
        whatever =>  rsa.importPrivate(SampleRSAData.privateKey)
      )
      it("should create same signature") {
        importedPriv.flatMap(key => {
          rsa.sign(SampleRSAData.message, key)
        }).map( signature => {
          signature should equal( SampleRSAData.signature)
        })
      }

      it("private should not be null") {
        importedPriv.map(key => {
          key should not be (null)
        })
      }
    }


  }

}
