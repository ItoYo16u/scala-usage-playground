package web.circe

import web.circe.values.{BookId, FlatBookJson}

object JsonParserWithCirce  {
  import io.circe.generic.auto._
  import io.circe.parser.decode
  // println(FlatBookJson(BookId(1),"title")) // => {"id":{"value" : 1},"title": "title"}


  def decodeBook(json: String)= {
    decode[FlatBookJson](json)
  }

}