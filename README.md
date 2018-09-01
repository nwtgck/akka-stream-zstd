# akka-stream-zstd
[![Build Status](https://travis-ci.com/nwtgck/akka-stream-zstd.svg?branch=develop)](https://travis-ci.com/nwtgck/akka-stream-zstd) [![Coverage Status](https://coveralls.io/repos/github/nwtgck/akka-stream-zstd/badge.svg?branch=develop)](https://coveralls.io/github/nwtgck/akka-stream-zstd?branch=develop) 

[Zstandard](https://facebook.github.io/zstd/) for [Akka Stream](https://doc.akka.io/docs/akka/2.5.5/scala/stream/index.html)

## Installation

Add the following lines to your `build.sbt`.

```scala
// Add dependency of `akka-stream-zstd.git` on GitHub
dependsOn(RootProject(uri("https://github.com/nwtgck/akka-stream-zstd.git#v0.1.1")))
```


## Example of compression

Here is an example to compress and store into a file

```scala
source
  // Compress
  .via(ZstdFlow.compress())
  // Store to file
  .runWith(FileIO.toPath(filePath))
```

[Full example](https://github.com/nwtgck/akka-stream-zstd-example/blob/965c98b708c0de22e8b256e24548d8cc87d1f33b/src/main/scala/Main.scala#L24-L29)

## Example of decompression

Here is an example to decompress from a stored file and convert into a `String`.

```scala
FileIO.fromPath(filePath)
  // Decompress
  .via(ZstdFlow.decompress())
  // Concatenate into one ByteString
  .runWith(Sink.fold(ByteString.empty)(_ ++ _))
  // Convert ByteString to String
  .map(_.utf8String)
```

[Full example](https://github.com/nwtgck/akka-stream-zstd-example/blob/965c98b708c0de22e8b256e24548d8cc87d1f33b/src/main/scala/Main.scala#L35-L42)

## Example repository

Here is a full example.

* [akka-stream-zstd-example](https://github.com/nwtgck/akka-stream-zstd-example)
