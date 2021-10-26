import java.io.File
import java.nio.file.{Files, Path, StandardCopyOption}
import scala.sys.process._
import xerial.sbt.Sonatype._
import scala.util.Try

val scala2Version = "2.13.6"
val scala3Version = "3.0.2"

credentials += Credentials("Sonatype Nexus Repository Manager", "s01.oss.sonatype.org", "sdrafahl", Try(scala.sys.env("NEXUS_PASSWORD")).getOrElse(""))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/sdrafahl/Codec"),
    "scm:git@github.com:sdrafahl/Codec.git"
  )
)
developers := List(
  Developer(
    id    = "sdrafahl",
    name  = "Shane Drafahl",
    email = "shanedrafahl@gmail.com",
    url   = url("https://github.com/sdrafahl/Codec")
  )
)

description := "Library for migrations" +  ""
licenses := Seq("MIT" -> url("https://github.com/sdrafahl/Codec/blob/master/LICENSE"))
homepage := Some(url("https://github.com/sdrafahl/Codec"))

pomIncludeRepository := { _ => false }

publishMavenStyle := true

sonatypeProjectHosting := Some(GitHubHosting("sdrafahl", "codec", "shanedrafahl@gmail.com"))
usePgpKeyHex("3E988A32587347BB874E66D9E30BD37D3C50EF1E")
ThisBuild / versionScheme := Some("pvp")

// lazy val root = project
//   .in(file("."))
//   .settings(
//     name := "codec",
//     version := "0.0.1",

//     libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test,    
//     libraryDependencies ++= genericCodecDependencies,
//     // To make the default compiler and REPL use Dotty
//     scalaVersion := scala3Version,

//     // To cross compile with Scala 3 and Scala 2
//     //crossScalaVersions := Seq(scala3Version, scala2Version)
//   ).dependsOn(CodecGeneric)

lazy val genericCodecDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.6.1"
)

lazy val CodecGeneric = (project in file("CodecGeneric"))
  .settings(
    name := "codecgeneric",
    libraryDependencies ++= genericCodecDependencies,
    scalaVersion := scala3Version,
    version := "0.0.1",
    publishTo := {
      val nexus = "https://s01.oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "service/local/staging/deploy/maven2")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }

  )

lazy val commonTestDependencies = Seq(
  "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test,
  "org.scalameta" %% "munit" % "0.7.29"
)

lazy val circeCodecConnectorDependencies = Seq(
  "io.circe" %% "circe-core" % "0.14.1",
  "io.circe" %% "circe-generic" % "0.14.1",
  "io.circe" %% "circe-extras" % "0.14.1"
)

lazy val CirceCodecConnector = (project in file("CirceCodecConnector"))
  .settings(
    name := "circecodecconnector",
    version := "0.0.1",
    libraryDependencies ++= genericCodecDependencies,
    libraryDependencies ++= circeCodecConnectorDependencies,
    libraryDependencies ++= commonTestDependencies,
    scalaVersion := scala3Version,
    publishTo := {
      val nexus = "https://s01.oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "service/local/staging/deploy/maven2")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }
  )
  .dependsOn(CodecGeneric)
