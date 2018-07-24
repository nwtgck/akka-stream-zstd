package io.github.nwtgck.akka_stream_zstd

import java.io.{PipedInputStream, PipedOutputStream}

import akka.NotUsed
import akka.stream.scaladsl.{Flow, StreamConverters}
import akka.util.ByteString
import com.github.luben.zstd.{ZstdInputStream, ZstdOutputStream}

object ZstdFlow {
  def compress(): Flow[ByteString, ByteString, NotUsed] = {
    val pout = new PipedOutputStream()
    val pin  = new PipedInputStream(pout)
    val compressOut = new ZstdOutputStream(pout)

    val sink = StreamConverters.fromOutputStream(() => compressOut)
    val source = StreamConverters.fromInputStream(() => pin)

    Flow.fromSinkAndSource(sink, source)
  }

  def decompress(): Flow[ByteString, ByteString, NotUsed] = {
    val pout = new PipedOutputStream()
    val pin  = new PipedInputStream(pout)
    val decompressIn = new ZstdInputStream(pin)

    val sink = StreamConverters.fromOutputStream(() => pout)
    val source = StreamConverters.fromInputStream(() => decompressIn)

    Flow.fromSinkAndSource(sink, source)
  }
}
