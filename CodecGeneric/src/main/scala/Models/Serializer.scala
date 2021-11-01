package io.github.sfrafahl.codec.generic

enum Serializer[+B] {
  case CustomSerializer[+A, B](serialize: (B => A)) extends Serializer[B]
  case GenericSerializer[+B]() extends Serializer[B]
}
