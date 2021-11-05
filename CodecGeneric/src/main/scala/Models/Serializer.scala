package io.codec.generic

sealed trait Serializer[+B]

object Serializer {
  case class CustomSerializer[+A, B](serialize: (B => A)) extends Serializer[B]
  case class GenericSerializer[+B]() extends Serializer[B]
}

