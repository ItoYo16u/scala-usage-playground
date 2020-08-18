import class_usages.implicits.sample._
import org.scalatest.{FunSpec,Matchers}

class BmiSpec extends FunSpec with Matchers {
  import NumberExtension._
  implicit val rule = new AppropriateBmiRule()

  describe("170.cm") {
    it("returns Cm(170)") {
      assert(170.cm === Cm(170))
    }
  }
  describe("60.kg") {
    it("return Kg(60)") {
      assert(60.kg === Kg(60))
    }
  }
  describe("Bmi") {
    describe("of(170.cm,60.kg") {
      it("returns bmi") {
        assert(Bmi.of(170.cm, 60.kg) === Bmi(20.76))
      }
    }
    describe("of(1.7.meter,60.kg") {
      it("is equal to Bmi.of(170.cm,60.kg)") {
        assert(Bmi.of(170.cm, 60.kg) === Bmi.of(1.7.meter, 60.kg))
      }
    }
    describe(" between 18.5 and 25") {
      describe("isAppropriate") {
        it("returns true") {
          assert(Bmi.of(170.cm, 60.kg).isAppropriate === true)
        }
      }
    }
    describe(" more than 25") {
      describe("isAppropriate") {
        it("returns false") {
          assert(Bmi.of(170.cm, 90.kg).isAppropriate === false)
        }
      }
    }
  }
}
