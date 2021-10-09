package com.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract class CirceCodecConnector {
  extension [A, B](genericCodec :com.codec.generic.Codec[A, B]) def circeCodecFromCodec(using CirceSerializerConnector, CirceParserConnector, Encoder[A], Decoder[A]): Codec[B]
}

object CirceCodecConnector {
  def apply()(using CirceCodecConnector) = summon[CirceCodecConnector]
  given CirceCodecConnector with
      extension [A, B](genericCodec :com.codec.generic.Codec[A, B]) def circeCodecFromCodec(using CirceSerializerConnector, CirceParserConnector, Encoder[A], Decoder[A]): Codec[B] = {
    val circeEncoder = genericCodec.Serializer.circeEncoderFromCodec
    val circeDecoder = genericCodec.Parser.circeDecoderFromCodec
    Codec.from(circeDecoder, circeEncoder)
  }
}
