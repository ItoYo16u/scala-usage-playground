package class_usages.time

case class TimeRange(start: Time, end:Time){
  // こんなのあるのか便利
  require(start <= end)

  def isInRange(target: Time):Boolean = {
    start <= target && target <= end
  }

  override def toString: String = "%s to %s".format(start,end)

}
