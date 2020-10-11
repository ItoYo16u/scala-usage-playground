package class_usages.time

case class Time(time: Double) extends TimeOperative[Time] with Ordered[Time] {

  override def toString(): String = {
    val hour   = Math.floor(time)
    val minute = (time - hour) * 60
    "Time(%02.0f:%02.0f)".format(hour, minute)
  }

  override def +(that: Duration): Time = Time(time + that.time)
  override def -(that: Duration): Time = Time(time - that.time)
  override def -(that: Time): Duration = Duration(this.time - that.time)

  override def compare(that: Time): Int = {
    this.time compare that.time
  }
}

object Time {

  def apply(str: String) = {
    val split                = str.split(":")
    val (hourStr, minuteStr) = (split(0), split(1))
    new Time(hourStr.toDouble + minuteStr.toDouble / 60)
  }

  def between(t1: Time, t2: Time): Time = {
    Time((t1.time + t2.time) / 2)
  }
}

trait TimeOperative[Time] {
  def +(that: Duration): Time
  def -(that: Duration): Time
  def -(that: Time): Duration
}
