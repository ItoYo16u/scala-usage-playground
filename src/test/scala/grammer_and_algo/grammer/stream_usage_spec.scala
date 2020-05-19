import org.scalatest.{FunSpec,Matchers}
import grammer_and_algo.grammer.StreamUsage

class StreamUsageSpec extends FunSpec with Matchers {
  describe("fib(1,1).take(7)"){
    it("returns Stream(1,?)"){
      StreamUsage.fib(1,1).take(7) shouldBe List(1,1,2,3,5,8,13)
    }
  }
  describe("numbersFrom(5).take(5).toList"){
    it("returns List(5,6,7,8,9,10)"){
      StreamUsage.numbersFrom(5).take(5).toList shouldBe List(5,6,7,8,9)
    }
  }
  describe("primes"){
    describe("(0)"){
      it("returns 2"){
        StreamUsage.primes(0) shouldBe 2
      }
    }
    describe("(304)"){
      it("returns 2011"){
        StreamUsage.primes(304) shouldBe 2011
      }
    }
    describe("(999)"){
      it("returns 7919"){
        StreamUsage.primes(999) shouldBe 7919
      }
    }
  }
}