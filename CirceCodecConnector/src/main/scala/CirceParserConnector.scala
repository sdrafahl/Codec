package com.codec.generic.circe

import io.circe.Decoder
import cats.implicits._

abstract class CirceParserConnector {
  extension [A, B](genericCodec :com.codec.generic.Parser[A, B]) def circeDecoderFromCodec(using baseDecoder: Decoder[A]): Decoder[B]
}

object CirceParserConnector {
  def apply[A, B]()(using CirceParserConnector): CirceParserConnector = summon[CirceParserConnector]
  given CirceParserConnector with
      extension [A, B](genericCodec :com.codec.generic.Parser[A, B]) def circeDecoderFromCodec(using baseDecoder: Decoder[A]): Decoder[B] = baseDecoder.emap(genericCodec.parse.andThen(validation => validation.toEither.left.map(_.show)))   
}
