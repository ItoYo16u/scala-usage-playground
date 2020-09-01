package class_usages.implicits.sample.rules

import class_usages.implicits.sample.rules.Rule.BoundsRule

object AppropriateBmiRule extends BoundsRule {
  override def lowerBound: Double = 18.5

  override def upperBound: Double = 25.0
}
