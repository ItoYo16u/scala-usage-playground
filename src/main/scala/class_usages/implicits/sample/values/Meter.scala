package class_usages.implicits.sample.values

case class Meter(private val v: Double) extends Height {
  require(0 < v)

  override def value: Double    = v
  override def toMeter(): Meter = copy()
  def meter: Meter              = Meter(v)

  override def toString: String = s"$value Meter"
}
