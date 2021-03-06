package io.codec.generic.circe

import munit.ScalaCheckSuite
import org.scalacheck.Prop._
import io.codec.generic.Codec
import munit.Clue.generate
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import io.codec.generic.circe.CirceCodecConnector.given

class CodecConnectorTest extends ScalaCheckSuite {    

  property("Generic Codec create default Circe Codec for RandomClass") {
    forAll { (randomClass: RandomClass) =>      
      val circeCodec = summon[io.circe.Codec[RandomClass]]
      val jsonOfrandomClass = circeCodec(randomClass)
      val decodedRandomClass = circeCodec.decodeJson(jsonOfrandomClass)
      decodedRandomClass == Right(randomClass)
    }
  }
}
