import org.scalatest.{FunSpec, Matchers}
import com.github.ItoYo16u.parsers.md_to_html.{PlainText, Strong,Link,Emphasis,Heading,ListItem,ListBlock}

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
  describe("Heading(n)"){
    it("returns <hn>content</hn>"){
      val headings: List[Heading]  = (1 to 6).map((n)=>Heading(n,Strong(PlainText("test")))).toList
      headings.zipWithIndex.foreach{case(headingN,idx)=>{
        println(headingN.toString)
        headingN.toString shouldBe s"<h${idx+1} ><strong >test</strong></h${idx+1}>"
      }}
    }
  }
  describe("list"){
    it("returns"){
      val l :ListBlock= ListBlock(
        List(
          ListItem(
            ListBlock(
              List(
                ListItem(
                  Strong(PlainText("aaa"))
                )
              )
            )
          ),
          ListItem(
            Strong(Emphasis(PlainText("bbb")))
          )
        )
      )
      l.toString shouldBe "<ul ><li ><ul ><li ><strong >aaa</strong></li></ul></li><li ><strong ><em >bbb</em></strong></li></ul>"
    }
  }

}
