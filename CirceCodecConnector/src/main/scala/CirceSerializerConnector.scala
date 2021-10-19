package com.codec.generic.circe

import io.circe.Encoder
import io.circe.Derivation.summonEncoder
import cats.implicits._
import com.codec.generic.Serializer

abstract class CirceSerializerConnector[B,T <: com.codec.generic.Serializer[B]] {
  def createEncoder(genericCodec:T): Encoder[B]
  extension (genericCodec:T) def circeEncoderFromCodec: Encoder[B] = createEncoder(genericCodec)
}

object CirceSerializerConnector {

  def apply[A, T <: com.codec.generic.Serializer[A]]()(using connector: CirceSerializerConnector[A, T]) = connector

  given [A, B](using baseEncoder: Encoder[A]): CirceSerializerConnector[B, Serializer.CustomSerializer[A, B]] with {
    def createEncoder(genericSerialize: Serializer.CustomSerializer[A, B]): Encoder[B] = Encoder.instance(genericSerialize.serialize.andThen(a => baseEncoder(a)))
  }

  given [B](using Encoder[B]): CirceSerializerConnector[B, Serializer.GenericSerializer[B]] with {
    def createEncoder(genericCodec: Serializer.GenericSerializer[B]): Encoder[B] = summonEncoder[B]
  }

}
