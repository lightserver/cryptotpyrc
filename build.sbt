import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.scalaJSUseMainModuleInitializer
import sbt.Keys.{crossScalaVersions, _}

scalaJSStage in Global := FastOptStage

val myVersion = "0.4.2"

lazy val supportedScalaVersions = List("2.12.1", "2.13.1")

scalaVersion in ThisBuild := "2.13.1"
organization in ThisBuild := "pl.setblack"
name in ThisBuild := "cryptotpyrc"
version in ThisBuild := myVersion

ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

ThisBuild / scalaVersion := "2.13.1"

lazy val root = project.in(file(".")).
  aggregate(app.js, app.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val app = crossProject(JSPlatform, JVMPlatform).in(file("app")).
  settings(
    name := "cryptotpyrc",
    unmanagedSourceDirectories in Compile +=
      baseDirectory.value / "shared" / "main" / "scala",

    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "1.0.0",
      "org.scalatest" %%% "scalatest" % "3.1.1" % "test"
    ),
    crossScalaVersions := supportedScalaVersions
  ).
  jvmSettings(
    // Add JVM-specific settings here
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.0.0",
      "org.scala-js" %% "scalajs-test-interface" % "1.0.0" % Test
    ),
    scalaJSUseMainModuleInitializer := false,
    test in Test := {} //js test disabled as we have no crypto api (run karmatests instead)
  )