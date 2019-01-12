name := "akka-stream-zstd"

version := "0.1.4"

val akkaVersion = "2.5.19"

crossScalaVersions := Seq(
  "2.11.12",
  "2.12.8"
)

libraryDependencies ++= Seq(
  "com.github.luben" % "zstd-jni" % "1.3.8-1",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,

  // ScalaTest
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)
