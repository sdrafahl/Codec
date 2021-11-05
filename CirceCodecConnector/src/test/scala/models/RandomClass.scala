package io.codec.generic.circe

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import io.codec.generic.Parser._
import io.codec.generic.Serializer._
import io.codec.generic._

case class RandomClass(name: String, numbers: List[Int])

object RandomClass {

  given genericCodec: Codec[RandomClass, GenericParser[RandomClass], GenericSerializer[RandomClass]] = Codec.createGenericCodec[RandomClass]

  private[this] val randomClassGen: Gen[RandomClass] = for {
    name <- Arbitrary.arbitrary[String]
    numbers <- Arbitrary.arbitrary[List[Int]]
  } yield RandomClass(name, numbers)
  given randomClassArb: Arbitrary[RandomClass] = Arbitrary(randomClassGen)
}
