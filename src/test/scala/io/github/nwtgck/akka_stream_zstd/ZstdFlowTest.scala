package io.github.nwtgck.akka_stream_zstd

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.Await
import scala.util.Random

class ZstdFlowTest extends FunSuite with Matchers {

  implicit val system: ActorSystem = ActorSystem("zstd-flow-test")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  test("compress") {
    import scala.concurrent.duration._

    val originalByteStr: ByteString =
      ByteString("hello, hello, hello, hello, hello, hello, world")

    val source = Source.single(originalByteStr)

    val fut = source
      // Compress
      .via(ZstdFlow.compress())
      // Concatenate
      .runWith(Sink.fold(ByteString.empty)(_ ++ _))

    // Get compressed data
    val compressed = Await.result(fut, 3.seconds)

    // Size of the compressed one should be less than original one
    compressed.size should be < originalByteStr.size
  }

  test("compress & decompress") {
    import scala.concurrent.duration._

    val originalByteStr: ByteString =
      ByteString("hello, hello, hello, hello, hello, hello, world")

    val source = Source.single(originalByteStr)

    val fut = source
      // Compress
      .via(ZstdFlow.compress())
      // Decompress
      .via(ZstdFlow.decompress())
      // Concatenate
      .runWith(Sink.fold(ByteString.empty)(_ ++ _))

    // Get final data
    val finalByteStr = Await.result(fut, 3.seconds)

    // The final data should be the same as the original
    finalByteStr shouldBe originalByteStr
  }

  test("compress & decompress big data") {
    import scala.concurrent.duration._

    // Create random 10MB data
    val originalByteStr: ByteString = ByteString({
      val bytes = new Array[Byte](10000000)
      Random.nextBytes(bytes)
      bytes
    })

    val source = Source.single(originalByteStr)

    val fut = source
      // Compress
      .via(ZstdFlow.compress())
      // Decompress
      .via(ZstdFlow.decompress())
      // Concatenate
      .runWith(Sink.fold(ByteString.empty)(_ ++ _))

    // Get final data
    val finalByteStr = Await.result(fut, 30.seconds)

    // The final data should be the same as the original
    finalByteStr shouldBe originalByteStr
  }
}
