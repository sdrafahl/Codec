# Codec
Codec is a low level library to represent parsing and decoding. Lots of Scala libraries have some form of Codec. Any library that requires serialization such as Doobie, Slick, Circe, Skunk, ect. This library hopes to unify all of these codecs under one Codec class and then derive those other codecs from this codec.

## Circe Example

```
// Class Definition 
case class RandomClass(name: String, numbers: List[Int])

object RandomClass {
  given genericCodec: Codec[RandomClass, GenericParser[RandomClass], GenericSerializer[RandomClass]] = Codec.createGenericCodec[RandomClass]
}

// In Use Example
val circeCodec = summon[io.circe.Codec[RandomClass]]
val jsonOfrandomClass = circeCodec(randomClass)
val decodedRandomClass = circeCodec.decodeJson(jsonOfrandomClass)
decodedRandomClass == Right(randomClass) // should be true


```


