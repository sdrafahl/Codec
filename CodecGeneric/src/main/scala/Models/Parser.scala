package io.codec.generic

import cats.data.Validated
import cats.Functor
import cats.implicits._

sealed trait Parser[+B]

sealed trait CustomParser[A, B] extends Parser[B] {
  def parse: A => Validated[ParsingError, B]
}

sealed trait GenericParser[B] extends Parser[B]

sealed trait MappedGenericParser[P, B] extends GenericParser[B] {
  def mapping: P => B
}

private[this] final case class GenericParserImpl[B]() extends GenericParser[B]
private[this] final case class MappedGenericParserImpl[P, B](mapping: P => B) extends MappedGenericParser[P, B]
private[this] final case class CustomParserImpl[A, B](parse: A => Validated[ParsingError, B]) extends CustomParser[A, B]  

object GenericParser {
  def apply[B]():GenericParser[B]  = GenericParserImpl[B]()
  given Functor[GenericParser] with {
    override def map[A, B](genericParser: GenericParser[A])(f: (A) => B): GenericParser[B] = MappedGenericParserImpl[A, B](f)
  }
}

object MappedGenericParser {
  type MappedGenericParserAB = [A] =>> [B] =>> MappedGenericParser[A, B]
  given [A]: Functor[MappedGenericParserAB[A]] with {
    override def map[B, C](genericParser: MappedGenericParser[A, B])(f: (B) => C): MappedGenericParser[A, C] = MappedGenericParserImpl(genericParser.mapping.andThen(f))
  }
}

object CustomParser {
  def apply[A, B](parse: A => Validated[ParsingError, B]) = CustomParserImpl(parse)  
  type CustomParserAB = [A] =>> [B] =>> CustomParser[A, B]
  given [A1]: Functor[CustomParserAB[A1]] with {
    override def map[A, B](customParser: CustomParser[A1, A])(f: (A) => B): CustomParser[A1, B] = CustomParserImpl[A1, B](customParser.parse.andThen(_.map(f)))
  }
}

object Parser {
  given Functor[Parser] with {
    override def map[A, B](genericParser: Parser[A])(f: (A) => B): Parser[B] = genericParser.map(f)
  }
}
