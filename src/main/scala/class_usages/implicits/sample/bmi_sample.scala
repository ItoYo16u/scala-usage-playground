
package class_usages.implicits.sample

final case class Cm(private val v: Int) extends Height {
  require(0 < v)

  override def value(): Double = v.toDouble

  override def toMeter(): Meter = Meter(v.toDouble / 100)

  def cm: Cm = Cm(v)
}

case class Meter(private val v: Double) extends Height {
  require(0 < v)

  override def value(): Double  = v
  override def toMeter(): Meter = copy()
  def meter: Meter              = Meter(v)
}

final case class Kg(value: Int) {
  require(0 < value)

  def kg: Kg = Kg(value)
}

trait BoundsRule {
  def lowerBound: Double

  def upperBound: Double
}

trait Height {
  def value(): Double
  def toMeter(): Meter
}

class AppropriateBmiRule extends BoundsRule {
  override def lowerBound: Double = 18.5

  override def upperBound: Double = 25.0
}

final case class Bmi(value: Double)(implicit boundsRule: BoundsRule) {
  def isAppropriate: Boolean = boundsRule.lowerBound < value && value < boundsRule.upperBound
}

object AppropriateBmiRule {
  def apply(): AppropriateBmiRule = new AppropriateBmiRule()
}

object Bmi {
  implicit val rule = new AppropriateBmiRule()

  def of(height: Height, weight: Kg): Bmi = {
    Bmi(
      BigDecimal(
        weight.value.toDouble / (height.toMeter().value() * height.toMeter().value())
      )
        .setScale(2, BigDecimal.RoundingMode.HALF_UP)
        .toDouble
    )
  }
}

object NumberExtension {
  implicit def intToCm(i: Int): Cm = Cm(i)

  implicit def doubleToMeter(d: Double): Meter = Meter(d)

  implicit def intToKg(i: Int): Kg = Kg(i)

}
