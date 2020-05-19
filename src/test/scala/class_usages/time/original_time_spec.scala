import class_usages.time.{Duration, Time, TimeRange}
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

  describe("TimeRange(start,end)"){
    it("returns err when start > end"){
      val start = Time("08:00")
      val end = Time("07:00")
      an[IllegalArgumentException] shouldBe thrownBy(TimeRange(start,end))
    }
    describe("#isInRange(Time)"){
      it("returns true if time is in range"){
        val t1 = Time(10)
        val t2 = Time(20)
        val range = TimeRange(Time(5),Time(17))
        range.isInRange(t1) shouldBe true
        range.isInRange(t2) shouldBe false
      }
    }
    describe("#toString"){
      it("returns formatted range"){
        val range = TimeRange(Time(2),Time(5))
        range.toString shouldBe "Time(02:00) to Time(05:00)"
      }
    }
  }
}