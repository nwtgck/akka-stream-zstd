name := "akka-stream-zstd"

version := "0.1.4"

scalaVersion := "2.11.12"

val akkaVersion = "2.5.19"

libraryDependencies ++= Seq(
  "com.github.luben" % "zstd-jni" % "1.3.8-1",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,

  // ScalaTest
  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)
