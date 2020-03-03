package pl.setblack.lsa.cryptotpyrc.rsa

import upickle.default.{ReadWriter => RW, macroRW}

case class JwkKey(
                   alg :String,
                   e :String,
                   ext :Boolean,
                   key_ops : Seq[String],
                   kty : String,
                   n : String)

object JwkKey {
  implicit val rw: RW[JwkKey] = macroRW
}
