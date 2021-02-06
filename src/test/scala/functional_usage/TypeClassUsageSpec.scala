package functional_usage
import org.scalatest.{FunSpecLike, Matchers}


class TypeClassUsageSpec extends  FunSpecLike with Matchers {
  import TypeClassUsage.personOrdering
  describe("List[Person]"){
    describe("#sort"){
      it("returns array  sorted by age"){
        val younger = Person(1)
        val middle = Person(2)
        val older = Person(3)
        val people = Seq(younger,older,middle)
        people.sorted shouldEqual Seq(younger,middle,older)
      }
    }
  }
}