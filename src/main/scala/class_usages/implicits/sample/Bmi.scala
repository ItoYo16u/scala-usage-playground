package class_usages.implicits.sample

import class_usages.implicits.sample.rules.Rule.BoundsRule
import class_usages.implicits.sample.rules._
import class_usages.implicits.sample.values._

import scala.util.{ Success, Try }

/**
  * Bmiをあらわすvalue object
  */
final case class Bmi(value: Double)(implicit boundsRule: BoundsRule) {
  def isAppropriate: Boolean = boundsRule.lowerBound < value && value < boundsRule.upperBound
}

/**
  * 身長と体重からBmiを生成するコンパニオンオブジェクト.
  */
object Bmi {
  implicit val rule = AppropriateBmiRule
  import class_usages.implicits.sample.extensions.NumberExtension._

  def of(height: Height)(weight: Weight): Try[Bmi] = {
    Try {
      Bmi(
        BigDecimal(
          weight.toKg.value / (height.toMeter.value * height.toMeter.value)
        )
          .setScale(2, BigDecimal.RoundingMode.HALF_UP)
          .toDouble
      )
    }
  }
  /*
  def appropriateWeightMinMax(h: Height): Try[MinMax[Weight]] = {
    val weightRange = (1 to 200).map(v => v.kg)
    val appropriateWeightList = weightRange
      .map(weight => (weight, Bmi.of(h)(weight))).filter {
      case (_, bmi) =>
        bmi match {
          case Success(_bmi) => _bmi.isAppropriate
          case _             => false
        }
    }.map { case (kg, bmi) => kg }
    Try {
      MinMax[Weight](appropriateWeightList.min, appropriateWeightList.max)
    }
  }
   */
}
