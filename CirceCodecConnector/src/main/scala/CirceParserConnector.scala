package io.codec.generic.circe

import io.circe.Decoder
import cats.implicits._
import io.codec.generic.Parser._
import io.circe.Derivation.summonDecoder
import io.codec.generic.Parser

abstract class CirceParserConnector[B ,T <: io.codec.generic.Parser[B]] {
  def createDecoder(genericCodec: T): Decoder[B]
  extension (genericCodec: T) def circeDecoderFromCodec: Decoder[B] = createDecoder(genericCodec)
}

object CirceParserConnector {
  given [A ,B](using baseDecoder: Decoder[A]): CirceParserConnector[B, CustomParser[A, B]] with {
    def createDecoder(customParser: CustomParser[A, B]): Decoder[B] = baseDecoder.emap(customParser.parse.andThen(validation => validation.toEither.left.map(_.show)))
  }

  given [B](using Decoder[B]): CirceParserConnector[B, GenericParser[B]] with {
    def createDecoder(genericCodec: GenericParser[B]): Decoder[B] = summonDecoder[B]
  }
}
