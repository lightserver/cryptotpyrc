package pl.setblack.lsa.cryptotpyrc.rsa

case class JwkKey(
                   alg :String,
                   e :String,
                   ext :Boolean,
                   key_ops : Seq[String],
                   kty : String,
                   n : String)
