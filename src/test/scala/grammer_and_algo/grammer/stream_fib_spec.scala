import org.scalatest.{FunSpec,Matchers}
import grammer_and_algo.grammer.StreamUsage

class StreamFibSpec extends FunSpec with Matchers {
  describe("fib(1,1).take(7)"){
    it("returns Stream(1,?)"){
      StreamUsage.fib(1,1).take(7) shouldBe List(1,1,2,3,5,8,13)
    }
  }
}