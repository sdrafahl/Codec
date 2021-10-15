package com.codec.generic

import cats.data.Validated

abstract class ParserEvaluator[SerializeType, ParsedType] {
  def parse(a: SerializeType)(using parser: Parser.CustomParser[SerializeType, ParsedType]): Validated[ParsingError, ParsedType]
}

object ParserEvaluator {
  def apply[A, B]()(using ParserEvaluator[A, B]): ParserEvaluator[A, B] = summon[ParserEvaluator[A, B]]
  def create[A, B]: ParserEvaluator[A, B] = {
    new ParserEvaluator[A, B] {
      def parse(a: A)(using parser: Parser.CustomParser[A, B]): Validated[ParsingError, B] = parser.parse(a)
    }
  }
}
