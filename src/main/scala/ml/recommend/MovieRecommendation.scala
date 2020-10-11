package ml.recommend

sealed trait Movie {
  val title: String
}

object Movie {

  def all(): Set[Movie] =
    Set(
      Movie.LadyInTheWater,
      Movie.SnakesOnAPlane,
      Movie.JustMyLuck,
      Movie.SupermanReturns,
      Movie.YouMeAndDupree,
      Movie.TheNightListener
    )

  final case object LadyInTheWater extends Movie {
    val title = "Lady in the Water"
  }

  final case object SnakesOnAPlane extends Movie {
    val title = "Snakes on a Plane"
  }

  final case object JustMyLuck extends Movie {
    val title = "Just My Luck"
  }

  final case object SupermanReturns extends Movie {
    val title = "Superman Returns"
  }

  final case object YouMeAndDupree extends Movie {
    val title = "You, Me and dupree"
  }

  final case object TheNightListener extends Movie {
    val title = "The Night Listener"
  }
}

final case class Score(value: Double) {
  def -(theOther: Score): Score = Score(value - theOther.value)
}

final case class Preferences(value: Map[Movie, Score]) {

  import cats.implicits._

  // TODO: 全く共通のものがない場合は0.0にする方が良い
  def calcDistanceFrom(theOther: Preferences): Double = {
    movies
      .foldLeft(Map.empty[Movie, Double]) { (acc, movie) =>
        val scoreOfThis     = preferenceOfThe(movie)
        val scoreOfTheOther = theOther.preferenceOfThe(movie)
        val diff = (scoreOfThis, scoreOfTheOther) mapN {
          _ - _
        }
        // Some - None = None
        // None - Some = None
        // None - None = None
        // Some + Some = Some
        // を実現している
        diff match {
          case None        => acc
          case Some(score) => acc + (movie -> math.pow(score.value, 2))
        }
      }.foldLeft(0: Double) { case (acc, (_, value)) => acc + value }
  }

  def movies: Set[Movie] = value.keys.toSet

  // todo: have movies private
  private def preferenceOfThe(movie: Movie): Option[Score] =
    value.get(movie) match {
      case Some(v) => v.some
      case None    => none[Score]
    }
}

final case class Customer(name: String, preferences: Preferences) {
  private def alreadyWatchedMovies: Set[Movie]                = preferences.movies
  def findUnwatchedMoviesFrom(theOther: Customer): Set[Movie] = theOther.alreadyWatchedMovies.diff(alreadyWatchedMovies)
}

final case class Similarity(value: Double) {
  require(0 < value && value <= 1)
}

object Similarity {

  def of(one: Customer, theOther: Customer): Similarity = {
    val distance = one.preferences.calcDistanceFrom(theOther.preferences)
    Similarity(1.0 / (1.0 + math.sqrt(distance)))
  }
}
