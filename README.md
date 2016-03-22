[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.8.svg)](https://www.scala-js.org)

#Cryptotpyrc

This ScalaJS cross compiled RSA / Crypto library.

## Supported
1. RSA Key generation
2. RSA Signing and Verifying

## Usage
```
val rsa = new UniCrypto.rsa
val generatedKeyPair = rsa.generateKeys()
...
generatedKeyPair.flatMap(
           keyPairGeneration => {
             val publicKeyJwk:Future[String] = keyPairGeneration.get.pub.export

             val privKeyPKCS8:Future[String] = keyPairGeneration.get.priv.export
             ...
           }
       )
...
val signature:Future[String] = rsa.sign(privKey, "mymessage" )
..
val verified:Future[Boolean] = rsa.verify(publKey, singature, "mymessage")
```
## Design
1. Use Strings in API -  make it easy
2. Use JVM Native and Browser native libraries (does not work on NodeJS)
3. Use Future(s) in API ( because of JavaScript)


