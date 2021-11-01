package io.github.sfrafahl.codec.generic.circe

import munit.ScalaCheckSuite
import org.scalacheck.Prop._
import io.github.sfrafahl.codec.generic.Parser
import io.github.sfrafahl.codec.generic.Serializer
import io.github.sfrafahl.codec.generic.Codec
import io.github.sfrafahl.codec.generic.Parser._
import io.github.sfrafahl.codec.generic.Serializer._
import munit.Clue.generate
import io.github.sfrafahl.codec.generic.circe.CirceSerializerConnector._
import io.circe.Derivation._
import io.circe.Decoder
import io.circe.Encoder
import io.circe.syntax._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import io.circe.Derivation._
import io.circe.generic.auto._
import io.github.sfrafahl.codec.generic.circe.CirceCodecConnector.given

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
