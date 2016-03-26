import sbt.Keys._

scalaJSStage in Global := FastOptStage

scalaJSUseRhino in Global  :=false

skip in packageJSDependencies := false

val myVersion = "0.3-SNAPSHOT"

scalaVersion := "2.11.8"
organization := "pl.setblack"
name := "cryptotpyrc"
version := myVersion

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

val app = crossProject.settings(
   scalaVersion := "2.11.8",
  organization := "pl.setblack",
  name := "cryptotpyrc",
  version := myVersion,

  unmanagedSourceDirectories in Compile +=
    baseDirectory.value  / "shared" / "main" / "scala",

  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "upickle" % "0.3.8",
    "org.scalatest" %%% "scalatest" % "3.0.0-M15" % "test"
  )

).jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0"
    ),
    jsDependencies ++= Seq(),

    skip in packageJSDependencies := false ,// creates app-jsdeps.js with the react JS lib inside
     persistLauncher in Compile := false
  ).jvmSettings(
    libraryDependencies ++= Seq(
      "org.bouncycastle" % "bcprov-jdk16" % "1.46",
      "org.scalaz" %% "scalaz-core" % "7.1.2"
    )
  )

lazy val appJS = app.js.settings(

  test in Test := {}
)

lazy val appJVM = app.jvm.settings(


  // copy resources like quiz.css to the server
  resourceDirectory in Compile <<= baseDirectory(_ / "../shared/src/main/resources"),

  // application.conf too must be in the classpath
  unmanagedResourceDirectories in Compile <+= baseDirectory(_ / "../jvm/src/main/resources")

).enablePlugins(JavaAppPackaging)
