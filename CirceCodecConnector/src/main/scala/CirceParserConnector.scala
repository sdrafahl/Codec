package com.codec.generic.circe

import io.circe.Decoder
import cats.implicits._
import com.codec.generic.Parser._
import io.circe.Derivation.summonDecoder
import com.codec.generic.Parser

abstract class CirceParserConnector[B ,T <: com.codec.generic.Parser[B]] {
  extension (genericCodec: T) def circeDecoderFromCodec: Decoder[B]
}

object CirceParserConnector {
  given [A ,B](using baseDecoder: Decoder[A]): CirceParserConnector[B, CustomParser[A, B]] with {
    extension (customParser: CustomParser[A, B]) def circeDecoderFromCodec: Decoder[B] = baseDecoder.emap(customParser.parse.andThen(validation => validation.toEither.left.map(_.show)))
  }

  given [B]: CirceParserConnector[B, GenericParser[B]] with {
    extension (genericCodec: GenericParser[B]) def circeDecoderFromCodec: Decoder[B] = summonDecoder[B]
  }
}
