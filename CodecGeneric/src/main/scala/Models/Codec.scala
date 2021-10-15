package com.codec.generic

case class Codec[B](parser: Parser[B], serializer: Serializer[B])
