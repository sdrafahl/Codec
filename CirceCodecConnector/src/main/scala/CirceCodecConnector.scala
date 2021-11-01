package io.github.sfrafahl.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract trait CirceCodecConnector[B, C <: io.github.sfrafahl.codec.generic.Parser[B], D <: io.github.sfrafahl.codec.generic.Serializer[B]] {
  def createCodec(genericCodec :io.github.sfrafahl.codec.generic.Codec[B, C, D])(using CirceSerializerConnector[B, D], CirceParserConnector[B, C]): Codec[B]
  given circeCodecFromCodec (using genericCodec: io.github.sfrafahl.codec.generic.Codec[B, C, D], csc: CirceSerializerConnector[B, D], cpc: CirceParserConnector[B, C]): Codec[B] = createCodec(genericCodec)
}

object CirceCodecConnector {
  def apply[B, C <: io.github.sfrafahl.codec.generic.Parser[B], D <: io.github.sfrafahl.codec.generic.Serializer[B]]()(using connector: CirceCodecConnector[B, C, D]) = connector

  given genericCirceCodec[
    B,
    C <: io.github.sfrafahl.codec.generic.Parser.GenericParser[B],
    D <: io.github.sfrafahl.codec.generic.Serializer.GenericSerializer[B]
  ](using
    cdc: CirceCodecConnector[B, C, D],
    genericCodec: io.github.sfrafahl.codec.generic.Codec[B, C, D],
    csc: CirceSerializerConnector[B, D],
    cpc: CirceParserConnector[B, C]): Codec[B] = cdc.circeCodecFromCodec

  given customCirceCodec[
    A,
    B,
    C <: io.github.sfrafahl.codec.generic.Parser.CustomParser[A, B],
    D <: io.github.sfrafahl.codec.generic.Serializer.CustomSerializer[A, B]
  ](using
    cdc: CirceCodecConnector[B, C, D],
    genericCodec: io.github.sfrafahl.codec.generic.Codec[B, C, D],
    csc: CirceSerializerConnector[B, D],
    cpc: CirceParserConnector[B, C]): Codec[B] = cdc.circeCodecFromCodec

  given createCustomCodec[A, B]: CirceCodecConnector[B, io.github.sfrafahl.codec.generic.Parser.CustomParser[A, B], io.github.sfrafahl.codec.generic.Serializer.CustomSerializer[A, B]] with {
    def createCodec(
      genericCodec :io.github.sfrafahl.codec.generic.Codec[
        B,
        io.github.sfrafahl.codec.generic.Parser.CustomParser[A, B],
        io.github.sfrafahl.codec.generic.Serializer.CustomSerializer[A, B]]
    )(using csc: CirceSerializerConnector[B, io.github.sfrafahl.codec.generic.Serializer.CustomSerializer[A, B]],
      cpc: CirceParserConnector[B, io.github.sfrafahl.codec.generic.Parser.CustomParser[A, B]]): Codec[B] =
      Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }

  given createGenericCodec[B]: CirceCodecConnector[B, io.github.sfrafahl.codec.generic.Parser.GenericParser[B], io.github.sfrafahl.codec.generic.Serializer.GenericSerializer[B]] with {
    def createCodec(
      genericCodec :io.github.sfrafahl.codec.generic.Codec[
        B,
        io.github.sfrafahl.codec.generic.Parser.GenericParser[B],
        io.github.sfrafahl.codec.generic.Serializer.GenericSerializer[B]]
    )(using csc: CirceSerializerConnector[B, io.github.sfrafahl.codec.generic.Serializer.GenericSerializer[B]],
      cpc: CirceParserConnector[B, io.github.sfrafahl.codec.generic.Parser.GenericParser[B]]): Codec[B] =
      Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }
}
