import org.scalatest.{FunSpec, Matchers}
import com.github.ItoYo16u.parsers.md_to_html.{PlainText, Strong,Link,Emphasis}

class TokenSpec extends FunSpec with Matchers {

  describe("Link(label,https://google.com).toString"){
    it("returns HTML"){
      val link = Link(PlainText("label"),"https://google.com")
      link.toString shouldBe "<a href='https://google.com'>label</a>"
    }
  }
  describe("Strong(PlainText(aaa)).toString"){
    it("returns HTML"){
      val strong = Strong(PlainText("aaa"))
      strong.toString shouldBe "<strong >aaa</strong>"
    }
  }
  describe("Strong(Emphasis(PlainText(test))"){
    it("returns HTML"){
      val strongEmphasis = Strong(Emphasis(PlainText("test")))
      strongEmphasis.toString shouldBe "<strong ><em >test</em></strong>"
    }
  }

}
