package grammer_and_algo.algo

import org.scalatest.{FunSpec, GivenWhenThen, Matchers}
class RecursiveNumSplitSpec extends FunSpec
  with Matchers
  with GivenWhenThen {
  describe("recursive_num_split(n:Int)"){
    it("splits a number into a list composed of each digit of the number until their sum becomes 1 digit"){
      Then("returns the sum of 1 digit number and the number from which 1 digit number was calculated ")
      // 98 -> 9+8 = 17
      // 17 -> 1+7 = 8
      // 17 + 8 = 25
      RecursiveMethod.num_split(98) shouldBe 25
      RecursiveMethod.num_split(3528) shouldBe 27
      RecursiveMethod.num_split(9514599) shouldBe 48
    }
  }
}