package class_usages.time

case class TimeRange(start: Time, end:Time){
  // こんなのあるのか便利
  require(start <= end)

  def isInRange(target: Time):Boolean = {
    start <= target && target <= end
  }

  def hasIntersection(that:TimeRange): Boolean = {
    if(this.start <= that.start) {
      that.start < this.end
    } else {
      this.start < that.end
    }
  }

  override def toString: String = "%s to %s".format(start,end)

}

object TimeRange {
  def apply(start:String,end:String) = {
    new TimeRange(Time(start),Time(end))
  }
}
