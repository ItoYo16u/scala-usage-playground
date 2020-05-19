package grammer_and_algo.algo

import org.scalatest.{FunSpec, GivenWhenThen, Matchers}
class RecursiveNumSplitSpec extends FunSpec
  with Matchers
  with GivenWhenThen {
  describe("num_split(n:Int)"){
    it("splits a number into a list composed of each digit of the number until their sum becomes 1 digit"){
      Then("returns the sum of 1 digit number and the number from which 1 digit number was calculated ")
      // 98 -> 9+8 = 17
      // 17 -> 1+7 = 8
      // 17 + 8 = 25
      RecursiveUsage.num_split(98) shouldBe 25
      RecursiveUsage.num_split(3528) shouldBe 27
      RecursiveUsage.num_split(9514599) shouldBe 48
    }
  }
  describe("permutation(List[Int])"){
    it("returns permutation of the List"){
      val l1=List(1,2,3)
      val l2 = List(1,2)
      RecursiveUsage.permutation(l1) shouldBe List(
        List(1,2,3),
        List(1,3,2),
        List(2,1,3),
        List(2,3,1),
        List(3,1,2),
        List(3,2,1)
      )
      RecursiveUsage.permutation(l2) shouldBe List(
        List(1,2),
        List(2,1)
      )
    }
  }
}