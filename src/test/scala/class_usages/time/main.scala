import java.io.InvalidClassException
import java.security.InvalidParameterException

import class_usages.time.{Duration, Time}
import org.scalatest._
import org.scalatest.FunSpec
class OriginalTimeClassSpec extends FunSpec with Matchers {

  describe("Time"){

    describe("#between(t1,t2)"){
      it("returns half of two Time"){
        val t1 = Time("08:00")
        val t2 = Time("09:00")
        (Time.between(t1,t2)) shouldBe Time(8.5)
      }
    }

    it("is comparable with compare operators"){
      val t1 = Time("10:00")
      val t2 = Time("11:00")
      val t3 = Time("10:00")
      (t1 < t2) shouldBe true
      (t2 < t3) shouldBe false
      (t3 == t1) shouldBe true
    }


    describe("(10:00)"){
      it("returns Time(10)"){
        val t = Time("10:00")
        t shouldBe Time(10)
      }
    }
    describe("#toString()"){
      it("returns formatted time") {
        val t = Time(1.5)
        val m = Time(0.2)
        t.toString() shouldBe "Time(01:30)"
        m.toString() shouldBe "Time(00:12)"
      }
    }
    describe("+ Duration"){
      it("add Duration to Time") {
        val t = Time("09:00")
        val d = Duration("00:15")
        (t + d) shouldBe Time("09:15")
      }
    }
  }
  describe("Duration"){

    it("is comparable with compare operators"){
      val t1 = Duration("10:00")
      val t2 = Duration("11:00")
      val t3 = Duration("10:00")
      (t1 < t2) shouldBe true
      (t2 < t3) shouldBe false
      (t3 == t1) shouldBe true
    }

    describe("toString"){
      it("returns formatted time"){
        Duration(0.2).toString() shouldBe "Time(00:12)"
      }
    }
  }
}