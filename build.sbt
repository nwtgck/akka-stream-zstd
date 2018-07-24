name := "akka-stream-zstd"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.12"

val akkaVersion = "2.5.14"

libraryDependencies ++= Seq(
  "com.github.luben" % "zstd-jni" % "1.3.5-2",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion
)
