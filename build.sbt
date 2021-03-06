version := "0.1"
scalaVersion := "2.12.7"
val circeVersion = "0.12.3"
lazy val akkaHttpVersion = "10.2.0"
lazy val akkaVersion = "2.6.8"



lazy val root = (project in file("."))
  .settings(
    name := "scala-usage-playground",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= Seq(
      "com.pauldijou" %% "jwt-core" % "0.18.0",
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.lihaoyi" %% "fastparse" % "2.2.2", // SBT
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "io.monix" %% "monix-reactive" % "2.3.3",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.typelevel" %% "cats-core" % "2.0.0",
    )
  )