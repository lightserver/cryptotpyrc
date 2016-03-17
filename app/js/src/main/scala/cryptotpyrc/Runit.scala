package cryptotpyrc

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport


object Runit extends js.JSApp{
  def main(): Unit = {
    val pk = new PKCSJS
    pk.generateKey()
    println("i tyle")
  }
}
