import class_usages.implicits.sample.{Bmi, MinMax}
import class_usages.implicits.sample.rules.{AppropriateBmiRule}
import class_usages.implicits.sample.values._
import org.scalatest.Matchers
import org.scalatest.FunSpec
import scala.util.Try

class BmiSpec extends FunSpec with Matchers {
  import class_usages.implicits.sample.extensions.NumberExtension._
  implicit val rule = AppropriateBmiRule

  describe("170.cm") {
    it("returns CentiMeter(170)") {
      assert(170.cm === CentiMeter(170))
    }
  }
  describe("60.kg") {
    it("return Kg(60)") {
      assert(60.kg === Kg(60))
    }
  }
  describe("Negative height") {
    it("throw exception") {
      assertThrows[IllegalArgumentException](
        -1.cm
      )
      assertThrows[IllegalArgumentException](
        -1.meter
      )
    }
  }

  describe("Bmi") {
    describe("of(170.cm,60.kg") {
      it("returns bmi") {
        assert(Bmi.of(170.cm)(60.kg) === Try(Bmi(20.76)))
      }
    }
    describe("of(100.cm,100.kg") {
      it("returns bmi") {
        assert(Bmi.of(100.cm)(100.kg) === Try(Bmi(100.00)))
      }
    }
    describe("of(1.7.meter,60.kg") {
      it("is equal to Bmi.of(170.cm,60.kg)") {
        assert(Bmi.of(170.cm)(60.kg) === Bmi.of(1.7.meter)(60.kg))
      }
    }
    describe(" between 18.5 and 25") {
      describe("isAppropriate") {
        it("returns true") {
          assert(Bmi.of(170.cm)(60.kg).get.isAppropriate === true)
        }
      }
    }
    describe(" more than 25") {
      describe("isAppropriate") {
        it("returns false") {
          assert(Bmi.of(170.cm)(90.kg).get.isAppropriate === false)
        }
      }
    }
  /*
    it("returns MinMax") {
      assert(Bmi.appropriateWeightMinMax(170.cm) === Try(MinMax(54.kg, 72.kg)))
    }
  */
  }
  describe("of(170.cm,54.kg) and of(170.cm,72.kg) ") {
    it("returns appropriate BMI values") {
      assert(Bmi.of(170.cm)(54.kg).get.isAppropriate && Bmi.of(170.cm)(54.kg).get.isAppropriate === true)
    }
  }

}