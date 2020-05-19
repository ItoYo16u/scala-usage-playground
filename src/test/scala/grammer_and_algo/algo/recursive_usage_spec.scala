package grammer_and_algo.algo

import org.scalatest.{FunSpec, GivenWhenThen, Matchers}
import org.scalatest.Matchers._
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
    it("returns Permutation of list elements"){
      When("list has a element/elements")

      val l1 = List(1)
      val l2 = List(1,2)
      val l3=List(1,2,3)
      RecursiveUsage.permutation(l1) shouldBe List(List(1))
      RecursiveUsage.permutation(l2) shouldBe List(
        List(1,2),
        List(2,1)
      )
      RecursiveUsage.permutation(l3) shouldBe List(
        List(1,2,3),
        List(1,3,2),
        List(2,1,3),
        List(2,3,1),
        List(3,1,2),
        List(3,2,1)
      )
    }
    it("returns empty List of empty list"){
      When("list has nothing")
      val l0 = List()
      RecursiveUsage.permutation(l0) shouldBe List(List())
    }
  }
  describe("binomial permutation(digits)"){
    it("returns a list of numbers composed of [0,1]"){
      RecursiveUsage.binomialPermutation(3) should contain allOf(
        "001",
        "000",
        "010",
        "011",
        "100",
        "101",
        "110",
        "111"
      )
      RecursiveUsage.binomialPermutation(3) should contain only(
        "001",
        "000",
        "010",
        "011",
        "100",
        "101",
        "110",
        "111"
      )
    }
  }
}