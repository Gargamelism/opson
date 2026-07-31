sbtVersion := "2.0.4"

ThisBuild / organization := "com.opson"
ThisBuild / scalaVersion := "3.8.4"
ThisBuild / version := "0.0.1"

lazy val opson = (project in file("."))
  .settings(
    name := "Opson",
    libraryDependencies ++= Seq(
        "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test
    )
  )