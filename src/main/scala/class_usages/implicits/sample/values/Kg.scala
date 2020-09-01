package class_usages.implicits.sample.values

final case class Kg(private val v: Int) extends Weight {
  require(0 < v)

  def kg: Kg = Kg(v)

  override def toKg: Kg = copy()

  override def value: Double = v.toDouble

  override def toString: String = s"$value Kg"
}