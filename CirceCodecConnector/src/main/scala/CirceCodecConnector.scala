package io.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract trait CirceCodecConnector[B, C <: io.codec.generic.Parser[B], D <: io.codec.generic.Serializer[B]] {
  def createCodec(genericCodec :io.codec.generic.Codec[B, C, D])(using CirceSerializerConnector[B, D], CirceParserConnector[B, C]): Codec[B]
  given circeCodecFromCodec (using genericCodec: io.codec.generic.Codec[B, C, D], csc: CirceSerializerConnector[B, D], cpc: CirceParserConnector[B, C]): Codec[B] = createCodec(genericCodec)
}

object CirceCodecConnector {
  def apply[B, C <: io.codec.generic.Parser[B], D <: io.codec.generic.Serializer[B]]()(using connector: CirceCodecConnector[B, C, D]) = connector

  given genericCirceCodec[
    B,
    C <: io.codec.generic.Parser.GenericParser[B],
    D <: io.codec.generic.Serializer.GenericSerializer[B]
  ](using
    cdc: CirceCodecConnector[B, C, D],
    genericCodec: io.codec.generic.Codec[B, C, D],
    csc: CirceSerializerConnector[B, D],
    cpc: CirceParserConnector[B, C]): Codec[B] = cdc.circeCodecFromCodec

  given customCirceCodec[
    A,
    B,
    C <: io.codec.generic.Parser.CustomParser[A, B],
    D <: io.codec.generic.Serializer.CustomSerializer[A, B]
  ](using
    cdc: CirceCodecConnector[B, C, D],
    genericCodec: io.codec.generic.Codec[B, C, D],
    csc: CirceSerializerConnector[B, D],
    cpc: CirceParserConnector[B, C]): Codec[B] = cdc.circeCodecFromCodec

  given createCustomCodec[A, B]: CirceCodecConnector[B, io.codec.generic.Parser.CustomParser[A, B], io.codec.generic.Serializer.CustomSerializer[A, B]] with {
    def createCodec(
      genericCodec :io.codec.generic.Codec[
        B,
        io.codec.generic.Parser.CustomParser[A, B],
        io.codec.generic.Serializer.CustomSerializer[A, B]]
    )(using csc: CirceSerializerConnector[B, io.codec.generic.Serializer.CustomSerializer[A, B]],
      cpc: CirceParserConnector[B, io.codec.generic.Parser.CustomParser[A, B]]): Codec[B] =
      Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }

  given createGenericCodec[B]: CirceCodecConnector[B, io.codec.generic.Parser.GenericParser[B], io.codec.generic.Serializer.GenericSerializer[B]] with {
    def createCodec(
      genericCodec :io.codec.generic.Codec[
        B,
        io.codec.generic.Parser.GenericParser[B],
        io.codec.generic.Serializer.GenericSerializer[B]]
    )(using csc: CirceSerializerConnector[B, io.codec.generic.Serializer.GenericSerializer[B]],
      cpc: CirceParserConnector[B, io.codec.generic.Parser.GenericParser[B]]): Codec[B] =
      Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }
}
