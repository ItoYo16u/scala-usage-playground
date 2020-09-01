package class_usages.implicits.sample.extensions

import class_usages.implicits.sample.values._

object NumberExtension {
  implicit def intToCm(i: Int): CentiMeter = CentiMeter(i)

  implicit def doubleToMeter(d: Double): Meter = Meter(d)

  implicit def intToKg(i: Int): Kg = Kg(i)
}
