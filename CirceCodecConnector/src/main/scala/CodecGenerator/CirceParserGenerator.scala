package com.codec.generic.circe

import cats.Show
import com.codec.generic.Parser
import io.circe._
import io.circe.Derivation._
import io.circe.syntax._
import cats.data.Validated
import com.codec.generic._

def circeDerivedCirceParser[A, B](using show: Show[A], circeParser: io.circe.Parser): Parser[A, B] = {
  val parse = (a: A) => {
    val serializationString = show.show(a)
    given decoderB: Decoder[B] = summonDecoder[B]
    val jsonOfSerializedString = circeParser.parse(serializationString)
    Validated.fromEither(
      jsonOfSerializedString
        .flatMap(_.as[B])
        .left
        .map(b => StringParsingErrorWithInput(b.toString, a))
    )
  }
  Parser.create(parse)
}

given circeAutoDervicedCirceParser[A, B](using show: Show[A], circeParser: io.circe.Parser): Parser[A, B] = circeDerivedCirceParser
