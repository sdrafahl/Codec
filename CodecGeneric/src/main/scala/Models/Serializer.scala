package com.codec.generic

enum Serializer[B] {
  case CustomSerializer[A, B](serialize: (B => A)) extends Serializer[B]
  case GenericSerializer extends Serializer
}
