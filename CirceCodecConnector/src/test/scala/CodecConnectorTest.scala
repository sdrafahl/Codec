package com.codec.generic.circe

import munit.ScalaCheckSuite
import org.scalacheck.Prop._
import com.codec.generic.Parser
import com.codec.generic.Serializer
import com.codec.generic.circe.CirceCodecConnector._
import com.codec.generic.Codec
import com.codec.generic.Parser._
import com.codec.generic.Serializer._
import munit.Clue.generate
import com.codec.generic.circe.CirceSerializerConnector._
import com.codec.generic.circe.CirceParserConnector._
import io.circe.Derivation._
import io.circe.Decoder
import io.circe.Encoder
import io.circe.syntax._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import io.circe.Derivation._
import io.circe.generic.auto._
import com.codec.generic.circe.CirceCodecConnector.given

class CodecConnectorTest extends ScalaCheckSuite {  
 
  val genericCodec = Codec.createGenericCodec[RandomClass]

  property("Generic Codec create default Circe Codec for RandomClass") {
    forAll { (randomClass: RandomClass) =>      
      val circeCodec = genericCodec.circeCodecFromCodec
      val jsonOfrandomClass = circeCodec(randomClass)
      val decodedRandomClass = circeCodec.decodeJson(jsonOfrandomClass)
      decodedRandomClass == Right(randomClass)
    }
  }
}
