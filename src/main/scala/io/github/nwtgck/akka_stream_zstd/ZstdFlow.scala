package io.github.nwtgck.akka_stream_zstd

import java.io.{PipedInputStream, PipedOutputStream}

import akka.NotUsed
import akka.stream.IOResult
import akka.stream.scaladsl.{Flow, Sink, StreamConverters}
import akka.util.ByteString
import com.github.luben.zstd.{ZstdInputStream, ZstdOutputStream}

import scala.concurrent.Future

object ZstdFlow {

  /**
    * Flow of compressing data by Zstandard
    * @param level
    * @param closeFrameOnFlush
    * @return
    */
  def compress(level: Int = 3, closeFrameOnFlush: Boolean = false): Flow[ByteString, ByteString, NotUsed] = {
    val pout        = new PipedOutputStream()
    val pin         = new PipedInputStream(pout)
    val compressOut = new ZstdOutputStream(pout, level, closeFrameOnFlush)

    val sink   = StreamConverters.fromOutputStream(() => compressOut)
    val source = StreamConverters.fromInputStream(() => pin)

    Flow.fromSinkAndSource(sink, source)
  }

  /**
    * Flow of decompressing data compressed by Zstandard
    * @return
    */
  def decompress(): Flow[ByteString, ByteString, NotUsed] = {
    val pout         = new PipedOutputStream()
    val pin          = new PipedInputStream(pout)
    val decompressIn = new ZstdInputStream(pin)

    val sink   = StreamConverters.fromOutputStream(() => pout)
    val source = StreamConverters.fromInputStream(() => decompressIn)

    Flow.fromSinkAndSource(sink, source)
  }
}
