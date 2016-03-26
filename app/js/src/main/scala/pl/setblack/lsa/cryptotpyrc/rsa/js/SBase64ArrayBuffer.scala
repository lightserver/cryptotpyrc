package pl.setblack.lsa.cryptotpyrc.rsa.js

import scala.scalajs.js
import scala.scalajs.js.typedarray.{Uint8Array, ArrayBuffer}

class SBase64ArrayBuffer {
  val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
  val lookup = recalc

  private def recalc:Uint8Array = {
    val result = new Uint8Array(256)
    for (i <- 0 to (chars.length-1)) {
      val code = chars.codePointAt(i)
      result(code) = i.toShort
    }
    result
  }


  def encode( byteBuffer : ArrayBuffer) : String =  {
    val bytes = new Uint8Array(byteBuffer.byteLength+2)
    val originalBytes = new Uint8Array(byteBuffer, 0, byteBuffer.byteLength)
    bytes.set(originalBytes, 0)
    val len = originalBytes.length
    var base64 = ""
    for (i <- 0 to  (len-1) by 3) {
    base64 += chars(bytes(i) >> 2)
      base64 += chars(((bytes(i) & 3) << 4) | (bytes(i + 1) >> 4))
      base64 += chars(((bytes(i + 1) & 15) << 2) | (bytes(i + 2) >> 6))
      base64 += chars(bytes(i + 2) & 63)
    }

    if ((len % 3) == 2) {
      base64 = base64.substring(0, base64.length - 1) + "="
    } else if (len % 3 == 1) {
      base64 = base64.substring(0, base64.length - 2) + "=="
    }
    base64
  }

  def decode( base64 : String ) : ArrayBuffer = {
    var bufferLength = (base64.length * 0.75).toInt
    val len = base64.length
    var p = 0
    var encoded1, encoded2, encoded3, encoded4 = 0.toShort

    if (base64(base64.length - 1) == '=') {
      bufferLength -= 1
      if (base64(base64.length - 2) == '=') {
        bufferLength -= 1
      }
    }

    val arraybuffer = new ArrayBuffer(bufferLength)
    val bytes = new Uint8Array(arraybuffer, 0, arraybuffer.byteLength)

    for (i <- 0 to (len-1) by 4) {

      encoded1 = lookup(base64.codePointAt(i))
      encoded2 = lookup(base64.codePointAt(i+1))
      encoded3 = lookup(base64.codePointAt(i+2))
      encoded4 = lookup(base64.codePointAt(i+3))

      bytes(p) = ((encoded1 << 2) | (encoded2 >> 4)).toShort
      p += 1
      bytes(p) = (((encoded2 & 15) << 4) | (encoded3 >> 2)).toShort
      p += 1
      bytes(p) = (((encoded3 & 3) << 6) | (encoded4 & 63)).toShort
      p += 1
    }

    arraybuffer
  }
}


object SBase64ArrayBuffer {
  val instance = new SBase64ArrayBuffer
}