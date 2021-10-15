package com.codec.generic.circe

import io.circe.Encoder
import io.circe.Derivation.summonEncoder
import cats.implicits._
import com.codec.generic.Serializer._

abstract class CirceSerializerConnector {
  extension [B](genericCodec :com.codec.generic.Serializer[B]) def circeEncoderFromCodec: Encoder[B]  
}

object CirceSerializerConnector {
  def apply[A, B]()(using CirceSerializerConnector): CirceSerializerConnector = summon[CirceSerializerConnector]
  given CirceSerializerConnector with
      extension [A, B](genericCodec :com.codec.generic.Serializer[B]) def circeEncoderFromCodec: Encoder[B] = genericCodec match {
    case customSerializer: CustomSerializer[A, B] => {
      val baseEncoder = summonEncoder[A]
      Encoder.instance(customSerializer.serialize.andThen(a => baseEncoder(a)))      
    }
    case GenericSerializer => summonEncoder[B]
  }
}
