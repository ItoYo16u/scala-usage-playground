package class_usages.implicits.sample.values

final case class CentiMeter(private val v: Int) extends Height {
  require(0 < v)

  override def value: Double = v.toDouble

  override def toMeter: Meter = Meter(v.toDouble / 100)

  def cm: CentiMeter = CentiMeter(v)

  override def toString: String = s"$value CentiMeter"
}