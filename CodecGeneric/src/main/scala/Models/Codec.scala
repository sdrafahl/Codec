package io.codec.generic

import io.codec.generic.Parser._
import io.codec.generic.Serializer._

sealed trait Codec[+B, +C <: Parser[B], +D <: Serializer[B]] {
  def parser: C
  def serializer: D
}

object Codec {
  private[Codec] case class CodecImpl[+B, +C <: Parser[B], +D <: Serializer[B]](parser: C, serializer: D) extends Codec[B, C, D]
  def createGenericCodec[B]: Codec[B, GenericParser[B], GenericSerializer[B]] = CodecImpl(GenericParser[B](), GenericSerializer())
  def createCodec[B](parser: Parser[B], serializer: Serializer[B]) = CodecImpl(parser, serializer)
}
