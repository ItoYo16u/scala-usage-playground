package web.circe.support

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.syntax._
import web.circe.values.{BookId, FlatBookJson}

trait BookSupport {
  implicit val bookIdEncoder: Encoder[BookId] = new Encoder[BookId] {
    override def apply(a: BookId): Json = a.value.asJson
  }
  implicit val bookIdDecoder:Decoder[BookId] = new Decoder[BookId] {
    override def apply(c: HCursor): Result[BookId] = c.value.as[Long].map(BookId)
  }
}