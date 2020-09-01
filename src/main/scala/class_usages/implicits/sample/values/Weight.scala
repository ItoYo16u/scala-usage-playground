package class_usages.implicits.sample.values

trait Weight extends Ordered[Weight] {

  import scala.math.Ordered.orderingToOrdered

  //  def compare(that: Weight): Int = if (this.value < that.value) -1 else if (this.value > that.value) 1 else 0
  override def compare(that: Weight): Int = this.value compare that.value

  def value: Double

  def toKg: Kg
}