import org.scalatest.FunSpec
import org.scalatest.GivenWhenThen
import org.scalatest.Matchers
import grammer_and_algo.algo._
class RecursiveNumSplitSpec extends FunSpec
  with Matchers
  with GivenWhenThen {
  describe("recursive_num_split(n:Int)"){
    it("splits a number into a list of 1 digit until their sum becomes 2 digits"){
      Then("returns (2 digit number + each digit of it)")

      RecursiveMethod.num_split(98) shouldBe 25
      RecursiveMethod.num_split(3528) shouldBe 27
      RecursiveMethod.num_split(9514599) shouldBe 48
    }
  }
}