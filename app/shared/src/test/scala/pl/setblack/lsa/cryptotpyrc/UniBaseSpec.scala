package pl.setblack.lsa.cryptotpyrc

import org.scalatest.{Matchers, AsyncFunSpec}
import pl.setblack.lsa.cryptotpyrc.rsa.{JwkKey, RSAPublicKey, RSAPrivateKey}

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
          rsa.sign(key, SampleRSAData.message )
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
    describe("generated key") {
      val generatedKeyPair = rsa.generateKeys()
      val exportedPublic = generatedKeyPair.flatMap( keys => {
        keys.pub.export
      })
      val exportedPriv = generatedKeyPair.flatMap( keys => {
        keys.priv.export
      })
      it( "public should be JwkFormat") {
        exportedPublic.map(keyExp => {
          upickle.default.read[JwkKey](keyExp).n should not be (empty)
        })
      }
      it( "private should be PKCS") {
        exportedPriv.map(keyExp => {
          keyExp should not be (empty)
        })
      }
    }

  }

}
