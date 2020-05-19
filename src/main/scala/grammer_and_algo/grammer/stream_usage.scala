package grammer_and_algo.grammer
import scala.collection.immutable.Stream
object StreamUsage {
  def fib(a:Int,b:Int):Stream[Int] = a #::fib(b,a+b)

  def numbersFrom(n:Int):Stream[Int] = Stream.cons(n,numbersFrom(n+1))

  def sieve(xs:Stream[Int]):Stream[Int] = {
    xs.head #:: sieve(xs.tail.filterNot(_% xs.head == 0))
  }
  def primes = sieve(numbersFrom(2))
}