package com.codec.generic.circe

import io.circe.Codec
import io.circe.Encoder
import io.circe.Decoder

abstract class CirceCodecConnector[B] {
  extension (genericCodec :com.codec.generic.Codec[B]) def circeCodecFromCodec(using CirceSerializerConnector[B, com.codec.generic.Serializer[B]], CirceParserConnector[B, com.codec.generic.Parser[B]]): Codec[B]
}

object CirceCodecConnector {  
  given [B]: CirceCodecConnector[B] with {
    extension (genericCodec :com.codec.generic.Codec[B]) def circeCodecFromCodec(using csc: CirceSerializerConnector[B, com.codec.generic.Serializer[B]], cpc: CirceParserConnector[B, com.codec.generic.Parser[B]]): Codec[B] = Codec.from(cpc.createDecoder(genericCodec.parser), csc.createEncoder(genericCodec.serializer))
  }
      
}
