import AsyncUsage.Product
import org.scalatest.{FunSpec, Matchers}

class AsyncUsageSpec extends FunSpec with Matchers {
  describe("AsyncUsageSpec"){
    describe("#build(name)"){
      it("returns Product(name)"){
        val name = "name"
        AsyncUsage.build(name) shouldBe Product(name)
      }
    }
  }
}
