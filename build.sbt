sbtVersion := "2.0.4"

ThisBuild / organization := "com.opson"
ThisBuild / scalaVersion := "3.8.4"
ThisBuild / version := "0.0.1"

lazy val opson = (project in file("."))
  .settings(
    name := "Opson",
    run / fork := true,
    libraryDependencies ++= Seq(
        "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
        "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
        "com.typesafe" % "config" % "1.4.9",
        "io.github.json4s" %% "json4s-native" % "4.1.1"
    )
  )