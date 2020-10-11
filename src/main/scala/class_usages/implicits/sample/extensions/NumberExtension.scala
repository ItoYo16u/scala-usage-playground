package class_usages.implicits.sample.extensions

import class_usages.implicits.sample.values._

/**
  * NumberExtension help construct Height and Weight instance in human readable style.
  * For example, you can build an instance of CentiMeter(100) with "100.cm".
  */
object NumberExtension {
  implicit def intToCm(i: Int): CentiMeter = CentiMeter(i)

  implicit def doubleToMeter(d: Double): Meter = Meter(d)

  implicit def intToKg(i: Int): Kg = Kg(i)
}
