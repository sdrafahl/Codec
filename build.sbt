val scala2Version = "2.13.6"
val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-cross",
    version := "0.1.0",

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test,    
    libraryDependencies ++= genericCodecDependencies,
    // To make the default compiler and REPL use Dotty
    scalaVersion := scala3Version,

    // To cross compile with Scala 3 and Scala 2
    // crossScalaVersions := Seq(scala3Version, scala2Version)
  )

lazy val genericCodecDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.6.1"
)

lazy val CodecGeneric = (project in file("CodecGeneric"))
  .settings(
    libraryDependencies ++= genericCodecDependencies,
    scalaVersion := scala3Version,
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
    libraryDependencies ++= genericCodecDependencies,
    libraryDependencies ++= circeCodecConnectorDependencies,
    libraryDependencies ++= commonTestDependencies,
    scalaVersion := scala3Version,
  )
  .dependsOn(CodecGeneric)
