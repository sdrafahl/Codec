package com.codec.generic

import cats.data.Validated

abstract class Parser[SerializeType, ParsedType] {
  def parse(a: SerializeType): Validated[ParsingError, ParsedType]
}

object Parser {
  def apply[A, B]()(using Parser[A, B]): Parser[A, B] = summon[Parser[A, B]]
  def create[A, B](fromi: (A => Validated[ParsingError, B])): Parser[A, B] = {
    new Parser[A, B] {
      def parse(a: A) = fromi(a)
    }
  }
}
