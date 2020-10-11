package class_usages.time

case class Duration(time: Double) extends DurationOperative[Duration] with Ordered[Duration] {

  override def toString(): String = {
    val hour   = Math.floor(time)
    val minute = (time - hour) * 60
    "Time(%02.0f:%02.0f)".format(hour, minute)
  }

  override def +(that: Duration): Unit = Duration(time + that.time)
  override def -(that: Duration): Unit = Duration(time - that.time)
  override def *(x: Double): Unit      = Duration(time * x)
  override def /(x: Double): Unit      = Duration(time / x)

  override def compare(that: Duration): Int = {
    this.time compare that.time
  }
}

object Duration {

  def apply(str: String) = {
    val split                = str.split(":")
    val (hourStr, minuteStr) = (split(0), split(1))
    new Duration(hourStr.toDouble + minuteStr.toDouble / 60)
  }
}

trait DurationOperative[Duration] {
  def +(that: Duration)
  def -(that: Duration)
  def *(x: Double)
  def /(x: Double)
}
