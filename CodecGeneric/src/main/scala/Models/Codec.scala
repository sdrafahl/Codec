package com.codec.generic

import com.codec.generic.Parser._
import com.codec.generic.Serializer._

case class Codec[B, C <: Parser[B], D <: Serializer[B]](parser: C, serializer: D)

object Codec {
  def createGenericCodec[B]: Codec[B, GenericParser[B], GenericSerializer[B]] = Codec(GenericParser(), GenericSerializer())
}
