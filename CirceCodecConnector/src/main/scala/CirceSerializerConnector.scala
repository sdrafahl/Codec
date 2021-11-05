package io.codec.generic.circe

import io.circe.Encoder
import io.circe.Derivation.summonEncoder
import cats.implicits._
import io.codec.generic.Serializer
import io.circe._, io.circe.generic.semiauto._
import scala.deriving.Mirror

abstract class CirceSerializerConnector[B, T <: io.codec.generic.Serializer[B]] {
  def createEncoder(genericCodec: T): Encoder[B]
  extension (genericCodec: T) def circeEncoderFromCodec: Encoder[B] = createEncoder(genericCodec)
}

object CirceSerializerConnector {

  def apply[A, T <: io.codec.generic.Serializer[A]]()(using connector: CirceSerializerConnector[A, T]) = connector

  inline given [A, B](using baseEncoder: Encoder[A]): CirceSerializerConnector[B, Serializer.CustomSerializer[A, B]] with {
    def createEncoder(genericSerialize: Serializer.CustomSerializer[A, B]): Encoder[B] = Encoder.instance(genericSerialize.serialize.andThen(a => baseEncoder(a)))
  }

  inline given [B](using m: Mirror.Of[B]): CirceSerializerConnector[B, Serializer.GenericSerializer[B]] = new CirceSerializerConnector[B, Serializer.GenericSerializer[B]] {
    def createEncoder(genericCodec: Serializer.GenericSerializer[B]): Encoder[B] = Encoder.AsObject.derived[B]
  }
}
