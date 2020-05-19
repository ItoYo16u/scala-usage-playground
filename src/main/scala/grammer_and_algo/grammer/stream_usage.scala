package grammer_and_algo.grammer
import scala.collection.immutable.Stream
object StreamUsage {
  def fib(a:Int,b:Int):Stream[Int] = a #::fib(b,a+b)
}