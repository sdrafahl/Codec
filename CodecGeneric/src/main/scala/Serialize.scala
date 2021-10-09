package com.codec.generic

import cats.data.Validated

abstract class Serializer[SerializeType, ParsedType] {
  def serialize(b: ParsedType): SerializeType  
}

object Serializer {
  def create[A, B]( serializei: (B => A)): Serializer[A, B] = {
    new Serializer[A, B] {
      def serialize(b: B) = serializei(b)
    }
  }
}
