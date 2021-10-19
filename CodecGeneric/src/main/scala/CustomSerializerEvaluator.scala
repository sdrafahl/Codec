package com.codec.generic

import cats.data.Validated

abstract class CustomSerializerEvaluator[SerializeType, ParsedType] {
  def serialize(b: ParsedType)(using serializer: CustomSerializer[SerializeType, ParsedType]): SerializeType  
}

object SerializerEvaluator {
  def create[A, B]: CustomSerializerEvaluator[A, B] = {
    new CustomSerializerEvaluator[A, B] {
      def serialize(b: B)(using serializer: CustomSerializer[A, B]): A = serializer.serialize(b)
    }
  }
}
