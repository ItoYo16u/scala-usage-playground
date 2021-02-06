package functional_usage

final case class Person(age:Int)

object TypeClassUsage {
  implicit val personOrdering = new Ordering[Person]{
    override def compare(x: Person, y: Person): Int = if(x.age > y.age) 1 else if(x.age==y.age) 0 else -1
  }
}