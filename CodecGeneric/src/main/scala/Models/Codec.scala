package io.github.sfrafahl.codec.generic

import io.github.sfrafahl.codec.generic.Parser._
import io.github.sfrafahl.codec.generic.Serializer._

case class Codec[+B, +C <: Parser[B], +D <: Serializer[B]](parser: C, serializer: D)

object Codec {
  def createGenericCodec[B]: Codec[B, GenericParser[B], GenericSerializer[B]] = Codec(GenericParser(), GenericSerializer())
}
