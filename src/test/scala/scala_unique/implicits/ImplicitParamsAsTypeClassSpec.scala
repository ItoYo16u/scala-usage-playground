package scala_unique.implicits
import org.scalatest.{FunSpec, Matchers}

class ImplicitParamsAsTypeClassSpec extends FunSpec with Matchers {
  describe("ImplicitParamsAsTypeClass"){
    describe("sum(List(1,2,3))"){
      it("returns 6"){
        ImplicitParamsAsTypeClass.sum(List(1,2,3)) shouldBe 6
      }
    }
    describe("sum(List('Hello','World','!'))"){
      it("returns HelloWorld!"){
        ImplicitParamsAsTypeClass.sum(List("Hello","World","!")) shouldBe "HelloWorld!"
      }
    }
  }
}