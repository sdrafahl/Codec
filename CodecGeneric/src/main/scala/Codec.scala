package com.codec.generic

import cats.data.Validated

abstract class Codec[SerializeType, ParsedType] {
  def parse(a: SerializeType): Validated[ParsingError, ParsedType]
  def serialize(b: ParsedType): SerializeType
  val Parser: Parser[SerializeType, ParsedType]
  val Serializer: Serializer[SerializeType, ParsedType]
}

object Codec {
  def apply[A, B]()(using Codec[A, B]): Codec[A, B] = summon[Codec[A, B]]
  def create[A, B](parser: Parser[A, B], serializer: Serializer[A, B]): Codec[A, B] = {
    new Codec[A, B] {
      override def parse(a: A) = parser.parse(a)
      override def serialize(b: B) = serializer.serialize(b)
      val Parser: Parser[A, B] = parser
      val Serializer: Serializer[A, B] = serializer
    }
  }
}
