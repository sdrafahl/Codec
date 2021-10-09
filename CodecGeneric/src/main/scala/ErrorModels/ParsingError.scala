package com.codec.generic

import cats.Show
import cats.implicits._

abstract class ParsingError

object ParsingError {
  sealed case class StringParsingErrorWithInput[A](message: String, input: A) extends ParsingError
  sealed case class StringParsingError(message: String) extends ParsingError

  given ParsingErrorShow: Show[ParsingError] = Show.fromToString[ParsingError] 
}

