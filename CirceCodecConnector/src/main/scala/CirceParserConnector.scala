package io.codec.generic.circe

import io.circe.Decoder
import cats.implicits._
import io.codec.generic.Parser._
import io.circe.Derivation.summonDecoder
import io.codec.generic._
import scala.deriving.Mirror
import io.circe.Decoder._
import io.circe.Derivation.summonDecoder

abstract class CirceParserConnector[B ,T <: io.codec.generic.Parser[B]] {
  def createDecoder(genericCodec: T): Decoder[B]
  extension (genericCodec: T) def circeDecoderFromCodec: Decoder[B] = createDecoder(genericCodec)
}

object CirceParserConnector {
  inline given [A ,B](using inline m: Mirror.Of[A]): CirceParserConnector[B, CustomParser[A, B]] = new CirceParserConnector[B, CustomParser[A, B]] {
    def createDecoder(customParser: CustomParser[A, B]): Decoder[B] = (Decoder.derived[A]).emap(customParser.parse.andThen(validation => validation.toEither.left.map(_.show)))
  }

  inline given[B](using inline m: Mirror.Of[B]): CirceParserConnector[B, GenericParser[B]] = new CirceParserConnector[B, GenericParser[B]] {
    def createDecoder(genericCodec: GenericParser[B]): Decoder[B] = Decoder.derived[B]
  }

  inline given[P, B](using inline m: Mirror.Of[P]): CirceParserConnector[B, MappedGenericParserImpl[P, B]] = new CirceParserConnector[B, MappedGenericParserImpl[P, B]] {
    def createDecoder(genericCodec: MappedGenericParserImpl[P, B]): Decoder[B] = Decoder.derived[P].map(genericCodec.mapping)
  }

  inline given[P, B](using inline m: Mirror.Of[P]): CirceParserConnector[B, MappedGenericParser[P, B]] = new CirceParserConnector[B, MappedGenericParser[P, B]] {
    def createDecoder(genericCodec: MappedGenericParser[P, B]): Decoder[B] = Decoder.derived[P].map(genericCodec.mapping)
  }
  
}
