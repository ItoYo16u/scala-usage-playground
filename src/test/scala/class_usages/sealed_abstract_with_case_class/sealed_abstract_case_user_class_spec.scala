import class_usages.sealed_abstract_with_case_class.{FinalUser, User}
import org.scalatest.{FunSpec, GivenWhenThen, Matchers}

class SealedAbstractCaseUserClassSpec extends FunSpec
  with Matchers with GivenWhenThen {
  describe("FinalUser: case class with final keyword"){
      describe("#create(1,name)"){
        it("returns Some[FinalUser(1,name)]"){

          FinalUser.create(1, "name") shouldBe Some(FinalUser(1,"name"))
        }
      }
      describe("#create(-1,name), negative id value"){
        it("returns None"){
          val noneUser =FinalUser.create(-1,"name")
           noneUser shouldBe None
          an[NoSuchElementException] shouldBe thrownBy(noneUser.get)
        }
      }
    describe("new FinalUser(-1,'name')"){
      it("can UNFORTUNATELY returns FinalUser(-1,'name')"){
        val illegalUser = new FinalUser(-1,"name")
        illegalUser shouldBe FinalUser(-1,"name")
      }
    }

    it("is impossible to create user by new keyword,() and apply() method"){
      When("user is sealed abstract case class")
    }
    describe("#create(-1,'test'"){
      it("returns None"){
        val noneUser = User.create(-1,"test")
        noneUser shouldBe None
        an[NoSuchElementException] shouldBe thrownBy(noneUser.get)
      }
    }
    describe("#create(1,'test')"){
      it("returns User(1,'test')"){
        val u = User.create(1,"test")
        u shouldBe a [Some[User]]
        u.get shouldBe a[User]
      }
      it("is comparable with other User class"){
        val u1 = User.create(1,"test")
        val u2 = User.create(2,"trial")
        val u3 = User.create(1,"test")
        (u1.get == u2.get) shouldBe false
        (u1.get == u3.get) shouldBe true
      }
      it("is matchable with unapply method"){
        val u = User.create(1,"trial")

        val matchResultWithUser = u.get match {
          case User(i,n)  => s"User id: $i,name: $n"
          case _ =>   "this is not a user"
        }
        matchResultWithUser shouldBe "User id: 1,name: trial"
      }
    }
  }
}