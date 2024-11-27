val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Palace",
    version := "1.0.0",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.2.16" % "test",
        "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
    ),
    parallelExecution in Test := false
  )
