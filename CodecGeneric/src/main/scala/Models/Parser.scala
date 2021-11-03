package io.codec.generic

import cats.data.Validated

enum Parser[+B] {
  case CustomParser[A, B](parse: A => Validated[ParsingError, B]) extends Parser[B]
  case GenericParser[B]() extends Parser[B]
}
