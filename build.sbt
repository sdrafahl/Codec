import java.io.File
import java.nio.file.{Files, Path, StandardCopyOption}
import scala.sys.process._
import xerial.sbt.Sonatype._
import scala.util.Try

val scala3Version = "3.0.2"

ThisBuild / versionScheme := Some("pvp")

publish / skip := true

val versionOfProject = "0.0.9"

lazy val genericCodecDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.6.1"
)

lazy val CodecGeneric = (project in file("CodecGeneric"))
  .settings(
    organization := "io.github.sdrafahl",
    name := "codecgeneric",
    libraryDependencies ++= genericCodecDependencies,
    scalaVersion := scala3Version,
    version := versionOfProject,
    credentials += Credentials("Sonatype Nexus Repository Manager", "s01.oss.sonatype.org", "sdrafahl", Try(scala.sys.env("SONATYPE_PASSWORD")).getOrElse("")),
    credentials += Credentials(
      "GnuPG Key ID",
      "gpg",
      "83CFB65722532521A286594CB8B3993C797D6D29", // key identifier
      "ignored" // this field is ignored; passwords are supplied by pinentry
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/sdrafahl/Codec"),
        "scm:git@github.com:sdrafahl/Codec.git"
      )
    ),
      developers := List(
        Developer(
          id    = "sdrafahl",
          name  = "Shane Drafahl",
          email = "shanedrafahl@gmail.com",
          url   = url("https://github.com/sdrafahl/Codec")
        )
      ),

    description := "Library for migrations" +  "",
      licenses := Seq("MIT" -> url("https://github.com/sdrafahl/Codec/blob/master/LICENSE")),
      homepage := Some(url("https://github.com/sdrafahl/Codec")),

    pomIncludeRepository := { _ => false },

    publishMavenStyle := true,

    sonatypeProjectHosting := Some(GitHubHosting("sdrafahl", "codec", "shanedrafahl@gmail.com")),
      
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
    organization := "io.github.sdrafahl",
    name := "circecodecconnector",
    version := versionOfProject,
    libraryDependencies ++= genericCodecDependencies,
    libraryDependencies ++= circeCodecConnectorDependencies,
    libraryDependencies ++= commonTestDependencies,
    scalaVersion := scala3Version,
    credentials += Credentials("Sonatype Nexus Repository Manager", "s01.oss.sonatype.org", "sdrafahl", Try(scala.sys.env("SONATYPE_PASSWORD")).getOrElse("")),
    credentials += Credentials(
      "GnuPG Key ID",
      "gpg",
      "83CFB65722532521A286594CB8B3993C797D6D29", // key identifier
      "ignored" // this field is ignored; passwords are supplied by pinentry
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/sdrafahl/Codec"),
        "scm:git@github.com:sdrafahl/Codec.git"
      )
    ),
      developers := List(
        Developer(
          id    = "sdrafahl",
          name  = "Shane Drafahl",
          email = "shanedrafahl@gmail.com",
          url   = url("https://github.com/sdrafahl/Codec")
        )
      ),

    description := "Library for migrations" +  "",
      licenses := Seq("MIT" -> url("https://github.com/sdrafahl/Codec/blob/master/LICENSE")),
      homepage := Some(url("https://github.com/sdrafahl/Codec")),

    pomIncludeRepository := { _ => false },

    publishMavenStyle := true,

    sonatypeProjectHosting := Some(GitHubHosting("sdrafahl", "codec", "shanedrafahl@gmail.com")),
      publishTo := {
        val nexus = "https://s01.oss.sonatype.org/"
        if (isSnapshot.value) Some("snapshots" at nexus + "service/local/staging/deploy/maven2")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
      }
  )
  .dependsOn(CodecGeneric)
