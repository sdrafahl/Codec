package io.github.sfrafahl.codec.generic.circe

import org.scalacheck.Gen
import org.scalacheck.Arbitrary

case class RandomClass(name: String, numbers: List[Int])

object RandomClass {
  private[this] val randomClassGen: Gen[RandomClass] = for {
    name <- Arbitrary.arbitrary[String]
    numbers <- Arbitrary.arbitrary[List[Int]]
  } yield RandomClass(name, numbers)
  given randomClassArb: Arbitrary[RandomClass] = Arbitrary(randomClassGen)
}
