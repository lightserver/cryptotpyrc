[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.8.svg)](https://www.scala-js.org)
[![Build Status](https://travis-ci.org/lightserver/cryptotpyrc.svg?branch=master)](https://travis-ci.org/lightserver/cryptotpyrc)
#Cryptotpyrc

This ScalaJS cross compiled RSA / Crypto library.

## Supported
1. RSA Key generation
2. RSA Signing and Verifying

## Usage
Add this to you build.xbt
```"pl.setblack" %%% "cryptotpyrc" % "0.4"```

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
## Features
1. Uses Strings in API -  makes it easy - not fast
2. Uses JVM (java.security) and browser (SubtleCrypto) native libraries (may not work on NodeJS)
3. Uses Future(s) in API (because of JavaScript) - crazy
4. Same keys and signatures will work on both JVM and  V8  ( You can sign on JVM and verify in browser or vice versa).
5. It supports only RSA as for now

## Testing
1. ```sbt test``` runs only JVM part of tests
2. to test in browser run ```sbt appJS/test:fastOptJS``` and then open provided test.html. (There are still issues with scalatest async tests, though.)

