package ml.recommend

import org.scalatest.{FunSpec, Matchers}

class MovieRecommendationSpec extends FunSpec with Matchers {
  describe("Score(n)"){
    describe("- Score(m)"){
      it("returns Score(n-m)"){
        Score(6) - Score(10) shouldBe Score(-4)
      }
    }
  }

  describe("Movie"){
    describe(("all")){
      it("returns all movies"){
        Movie.all() shouldBe Set(
          Movie.LadyInTheWater,
          Movie.SnakesOnAPlane,
          Movie.JustMyLuck,
          Movie.SupermanReturns,
          Movie.YouMeAndDupree,
          Movie.TheNightListener
        )
      }
    }
    describe("== the sameMovie"){
      it("returns true"){
        Movie.LadyInTheWater should equal(Movie.LadyInTheWater)
      }
    }
    describe("== another Movie"){
      it("returns false"){
        Movie.LadyInTheWater should not be (equals(Movie.SnakesOnAPlane))
      }
    }
  }
  describe("Preferences"){
    describe("movies"){
      it("returns Set[Movie]"){
        val pref = Preferences(value = Map(Movie.LadyInTheWater -> Score(1.5), Movie.SnakesOnAPlane -> Score(2.0)))
        pref.movies should not be empty
        pref.movies should contain allOf(Movie.LadyInTheWater, Movie.SnakesOnAPlane)
        pref.movies should contain noneOf(Movie.JustMyLuck, Movie.SupermanReturns, Movie.YouMeAndDupree, Movie.TheNightListener)
      }
    }
    describe("calcDistanceFrom(self)"){
      it("returns 0"){
        val pref1 = Preferences(value = Map(Movie.LadyInTheWater -> Score(1.5), Movie.SnakesOnAPlane -> Score(2.0)))
        pref1.calcDistanceFrom(pref1) shouldBe 0
      }
    }
    describe("calcDistanceFrom(preferenceWithoutCommon)"){
      it("returns 0"){
        // 共通で見た者がない場合はdistance1のほうがいいのだろうか...
        // TODO: Write test
      }
    }
  }
}