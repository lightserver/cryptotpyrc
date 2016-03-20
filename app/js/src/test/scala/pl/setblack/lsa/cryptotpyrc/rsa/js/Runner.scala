package pl.setblack.lsa.cryptotpyrc.rsa.js

import org.scalajs.testinterface.internal.Slave

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalatest._

@js.native
object scalajsCom extends js.Object{
  def receive(command : String) : Unit  = js.native

}

@JSExport
object Runner {

  @JSExport
  def main() = {
    val slave = new Slave("org.scalatest.tools.Framework", js.Array() , js.Array())
    slave.init()
    scalajsCom.receive("newRunner:")
    val cmd = makeRunCmd("pl.setblack.lsa.cryptotpyrc.rsa.js.RSACryptoAlgTest")
    println(cmd)
    scalajsCom.receive("execute:"+cmd)
  }

  def makeTask( name : String ) = {
    s"""{"fullyQualifiedName": "${name}",
    "fingerprint": {
      "fpType": "SubclassFingerprint",
      "superclassName": "org.scalatest.Suite",
      "isModule": false,
      "requireNoArgConstructor": true
    },
    "explicitlySpecified": true,
    "selectors": [{"selType": "SuiteSelector"}]}"""
  }

  def makeRunCmd(name : String) = {
    val task = makeTask(name).replaceAll("\"", "\\\\\"").replaceAll("\n", "")
    s"""{
        "loggerColorSupport": [],
        "serializedTask": "${task}"
       }"""
  }

}

case class RunTask(

                  )
