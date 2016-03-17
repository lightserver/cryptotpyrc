package pl.setblack.lsa.cryptotpyrc

import java.math.BigInteger
import java.security.spec.{PKCS8EncodedKeySpec, AlgorithmParameterSpec, RSAPublicKeySpec}
import java.security._
import java.util
import java.util.Base64

import sun.security.rsa.RSAPublicKeyImpl

import scala.concurrent.Promise




class PKCSJVM {

}


object PKCSJVM extends App {

  override
  def main(args: Array[String]): Unit = {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      println("jedziem")

    val  data = "I have nothing to hide"
    val keyGen = KeyPairGenerator.getInstance("RSA")
    keyGen.initialize(1024)
    val keyPair = keyGen.generateKeyPair();
    val pub = keyPair.getPublic.asInstanceOf[RSAPublicKeyImpl]
    val modulus = pub.getModulus
    val expo  = pub.getPublicExponent

    val myNewRsa = KeyFactory.getInstance("RSA").generatePublic( new RSAPublicKeySpec(modulus, expo))


    val signature = Signature.getInstance("SHA256withRSA", "BC")
    signature.initSign(keyPair.getPrivate)
    signature.update(data.getBytes())
    val jvmSIgnatur = signature.sign()

   signature.initVerify(myNewRsa)
    signature.update(data.getBytes())
    val jvmRes = signature.verify(jvmSIgnatur)
    println(s"intenral test : ${jvmRes}")
   println(new String(Base64.getUrlEncoder.encode(modulus.toByteArray)))


    val bigInt = new java.math.BigInteger("314155926537", 10)
    println("BIGINT=" + new String(bigInt.toString(10)))

    val keyEncoded = new String(Base64.getEncoder.encode(keyPair.getPublic.getEncoded))
    println(s"keyEncoded: ${keyEncoded}")
    println(keyPair.getPublic.getFormat)

    println("z przegladarki")

      val key  = buildPublicKey


    //val params = new RSA ( )
    signature.initVerify(key)

    signature.update(makeBytesMsg(data))

      val result = signature.verify(getSign)
     println(s"result ${result}")

     signature.initSign(buildPrivKey)
    signature.update(makeBytesMsg(data))
    val signed =   new String(Base64.getEncoder.encode(signature.sign()))

    println("signed:"+ signed)
  }

  def makeBytesMsg(msg : String) = {
    val result = msg.getBytes("UTF-16LE")

    println(util.Arrays.toString(result))

    result
  }

  def getSign = {
    val sign = "QHCdk1tomaCf3Ihrg+x0qaGpVM7+tja/Epl7pjndMgS5Px3qrGXewgDHgqcFZxPueR9PWooNwMTZl9dDMH1wUhtAzPYjlU9A5NXjukR8fviY2d/y6H0P+xOeFQDinGwERZheGe8ekBbotazWUcdGf8cOLOfSQsfskIIthkipJOE="
    val sign2 = "zslIykNEa2yut0G6zKf9xwsljfQKheY2ySbQCfA0WhFv1P/auSqkMPfwx0+1PC1sSdlSgUSvFMRtR1YipEh8xJ+fMOce+CnW9BGL3LwJqJvx7VAWw/UwG27hfFHsNlWBN/fijUJ+0Y36RoB0H3pBH8tp5/6Bm0RgUnpM9M7yCkdWBRj3ppyhi5Y3XY8IBbusPh2igPv0QUsicK8VdYOwMMygF1DEp5pUQ5TSFDXWXuAy1mZadqLMv0gfGZlj6JCWfrYNfOAy1iIyYxEx5HDzuSnhsH3WN8TtEaTqsXS8cCgjp5PlTjsvw9OeANexYLrIIUwqC0eLBUaF7DfAeG6oVQ=="
    Base64.getDecoder.decode(sign)
  }

  def buildPublicKey :  java.security.PublicKey = {
    val n = "rznZUSx8rz2_9UYHiPNwZlp2O5cImXgNwpkKdQkMhl7H2u413IvLex48ZkVTeLkO2mbBlFTyIXiSHbMzgTCDNCJxZqKxh6gOsba2vJ43VxUh632Xe_y-DmJtbuUXr4ll5-5oavaPHVpDiLVmw4sVMB_7elvQRXSD0j_CIy5-pBE"
    val e = "AQAB"

    val modulus = new BigInteger( 1, Base64.getUrlDecoder.decode(n))
    val exponent = new BigInteger( 1, Base64.getUrlDecoder.decode(e))
    return KeyFactory.getInstance("RSA", "BC").generatePublic( new RSAPublicKeySpec(modulus, exponent))
  }


  def buildPrivKey : java.security.PrivateKey = {
    val w = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK852VEsfK89v/VGB4jzcGZadjuXCJl4DcKZCnUJDIZex9ruNdyLy3sePGZFU3i5DtpmwZRU8iF4kh2zM4EwgzQicWaisYeoDrG2tryeN1cVIet9l3v8vg5ibW7lF6+JZefuaGr2jx1aQ4i1ZsOLFTAf+3pb0EV0g9I/wiMufqQRAgMBAAECgYBjeY7Sk1PRVFfmUTQz3iegI5jdaXebUFlXcPRLzrBKlAIXWoY/RylkIp0dXhvt+/jfqdgZckPnFoC01afWnojMnn6AM8xq/kV5I9kY8ax+qfz8ISHNfaJraCvqqOFu2HU6aBfzWB2DJtRWQ+tMIUEMWfiv2eEoYsFhgvMHnjPmsQJBANgrTf9HsSSirS+R+/sYjgCF/8iUnt+ucHKjWtDV5K7G6AE7jHtC0gHa0BTLLQ8HiBEmsYYmiG8w3zR7IW+OTe0CQQDPg0E9Zr/5WUPCnNZqnnhWgkSwT+PTf8DDYOSTVLWnrps1xad0mNyqV5umjIS/vQ67p+b9P4nQg/klIbWBSko1AkB+9N/4H8jf9VK1b42IKSGtGQXibbbpM/ACxAHgSN7n7xXvM2QMOHBSeopey/0IqKm3OPxmB1d1xkQw9Gfstqz5AkAw0MazJA27IbWf/XcvQ7I5X5G7DEAjC+WT0KJUylOla5zVjHa1JhSa+dsC1gGQfXAO0xOytZS8+Z7Njl19WbWVAkAmFWmizfAEoqy5uidu1nTJTaoEDuwr0HeiRg8+e7CQ9wY1NcoHfd4UFsRzApkl5VsTokzSwoZb7/uMmSeCA3mc"
    return KeyFactory.getInstance("RSA").generatePrivate( new PKCS8EncodedKeySpec(Base64.getDecoder.decode(w)))
  }
}
