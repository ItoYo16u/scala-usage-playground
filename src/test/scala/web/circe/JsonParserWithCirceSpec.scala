package web.circe
import org.scalatest.{FunSpec, Matchers}
import web.circe.values.{BookId, FlatBookJson}

class JsonParserWithCirceSpec extends  FunSpec with Matchers {
  describe("JsonParserWithCirceSpec"){
    describe("decode(validJson)"){
      it("returns FlatBookJson obj"){
        val flatJson: String ="""{
            | "id": 1,
            | "title": "Scalaスケーラブルプログラミング"
            | }""".stripMargin
        val nestedJson :String =
        """
           {
          | "id": {
          |   "value": 1
          | },
          | "title": "Scalaスケーラブルプログラミング"
          | }
        """.stripMargin
        JsonParserWithCirce.decodeBook(nestedJson) shouldBe Right(FlatBookJson(BookId(1),"Scalaスケーラブルプログラミング"))
        JsonParserWithCirce.decodeBook(flatJson) shouldBe a[Left[_,_]]
      }
    }
  }
}