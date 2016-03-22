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
             val publicKey = keyPairGeneration.get.pub

             val privKey = keyPairGeneration.get.priv.export.foreach(pk => println("PRIV:" + pk))

            publicKey.export
           }
       )
...
val signature:Future[String] = rsa.sign(privKey, "mymessage" )
..
val verified:Future[Boolean] = rsa.verify(publKey, singature, "mymessage")
```



