package com.codec.generic.circe

import io.circe.Encoder
import cats.implicits._

abstract class CirceSerializerConnector {
  extension [A, B](genericCodec :com.codec.generic.Serializer[A, B]) def circeEncoderFromCodec(using baseEncoder: Encoder[A]): Encoder[B]  
}

object CirceSerializerConnector {
  def apply[A, B]()(using CirceSerializerConnector): CirceSerializerConnector = summon[CirceSerializerConnector]
  given CirceSerializerConnector with
      extension [A, B](genericCodec :com.codec.generic.Serializer[A, B]) def circeEncoderFromCodec(using baseEncoder: Encoder[A]): Encoder[B] = Encoder.instance(genericCodec.serialize.andThen(a => baseEncoder(a)))
}
