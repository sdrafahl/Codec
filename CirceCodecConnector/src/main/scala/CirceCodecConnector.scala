package com.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract class CirceCodecConnector {
  extension [A, B](genericCodec :com.codec.generic.Codec[B]) def circeCodecFromCodec(using CirceSerializerConnector, CirceParserConnector): Codec[B]
}

object CirceCodecConnector {
  def apply()(using CirceCodecConnector) = summon[CirceCodecConnector]
  given CirceCodecConnector with
      extension [A, B](genericCodec :com.codec.generic.Codec[B]) def circeCodecFromCodec(using CirceSerializerConnector, CirceParserConnector): Codec[B] = {
    val circeEncoder: Decoder[B] = genericCodec.parser.circeDecoderFromCodec
    val circeDecoder: Encoder[B] = genericCodec.serializer.circeEncoderFromCodec
    Codec.from(circeEncoder, circeDecoder)
  }
}
