import sbt.Keys._

scalaJSStage in Global := FastOptStage

skip in packageJSDependencies := false

val myVersion = "0.4.1"

scalaVersion in ThisBuild := "2.12.1"
organization in ThisBuild := "pl.setblack"
name in ThisBuild := "cryptotpyrc"
version in ThisBuild := myVersion

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

  unmanagedSourceDirectories in Compile +=
    baseDirectory.value / "shared" / "main" / "scala",

  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "upickle" % "0.4.4",
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test"
  )
).jsSettings(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  ),
  jsDependencies ++= Seq(),
  skip in packageJSDependencies := false,
  persistLauncher in Compile := false
).jvmSettings(
  libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "7.2.8"
  )
)

lazy val appJS = app.js.settings(

  test in Test := {}
)

lazy val appJVM = app.jvm.settings(

  resourceDirectory in Compile <<= baseDirectory(_ / "../shared/src/main/resources"),

  unmanagedResourceDirectories in Compile <+= baseDirectory(_ / "../jvm/src/main/resources")

).enablePlugins(JavaAppPackaging)
