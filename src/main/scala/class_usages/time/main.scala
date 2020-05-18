package class_usages.time

object Main extends App {
 val time = Time(10)
  val duration = Duration(0.5)
  println(s"duration: $duration")
  println(s"duration.time: ${duration.time}")
  time.toString
}
