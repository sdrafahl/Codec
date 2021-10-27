package io.github.sfrafahl.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract class CirceCodecConnector[B, C <: io.github.sfrafahl.codec.generic.Parser[B], D <: io.github.sfrafahl.codec.generic.Serializer[B]] {
  def createCodec(genericCodec :io.github.sfrafahl.codec.generic.Codec[B, C, D])(using CirceSerializerConnector[B, D], CirceParserConnector[B, C]): Codec[B]
  extension (genericCodec: io.github.sfrafahl.codec.generic.Codec[B, C, D]) def circeCodecFromCodec(using CirceSerializerConnector[B, D], CirceParserConnector[B, C]): Codec[B] = createCodec(genericCodec)    
}

object CirceCodecConnector {
  def apply[B, C <: io.github.sfrafahl.codec.generic.Parser[B], D <: io.github.sfrafahl.codec.generic.Serializer[B]]()(using connector: CirceCodecConnector[B, C, D]) = connector
  given createCirceCodecConnector[B, C <: io.github.sfrafahl.codec.generic.Parser[B], D <: io.github.sfrafahl.codec.generic.Serializer[B]]: CirceCodecConnector[B, C, D] with {
    def createCodec(genericCodec :io.github.sfrafahl.codec.generic.Codec[B, C, D])(using csc: CirceSerializerConnector[B, D], cpc: CirceParserConnector[B, C]): Codec[B] = Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }
}
