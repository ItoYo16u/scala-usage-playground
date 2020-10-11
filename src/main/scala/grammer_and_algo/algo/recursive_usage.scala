package grammer_and_algo.algo

object RecursiveUsage {

  def num_split(n: Int): Int = {
    val n_as_arr: Array[String] = n.toString.split("")
    val sum: Int = n_as_arr.foldLeft(0: Int)((acc, s) => {
      acc + s.toInt
    })
    if (sum > 10) {
      num_split(sum)
    } else {
      n + sum
    }
  }

  def permutation[T](l: List[T]): List[List[T]] = {
    l match {
      case Nil                 => return List(List())
      case head :: Nil         => return List(List(head))
      case head :: tail :: Nil => return List(List(head, tail), List(tail, head))
      case _ =>
        l.foldRight(Nil: List[List[T]])((elm, acc) => {
          permutation(l.filterNot(_ == elm))
            .map(sub => { elm :: sub })
            .foldRight(acc)((list, result) => {
              list :: result
            })
        })
    }
  }

  def binomialPermutation(digit: Int): List[String] = {
    digit match {
      case 1 => List("1", "0")
      case _ =>
        binomialPermutation(digit - 1)
          .foldRight(Nil: List[String])((z, acc) => {
            ("0" + z) :: ("1" + z) :: acc
          })
    }
  }

  def nDigitPermutation(digit: Int, n: Int): List[List[Int]] = {
    digit match {
      case 1 => (0 to (n - 1)).map(k => List(k)).toList
      case _ =>
        nDigitPermutation(digit - 1, n).foldLeft(Nil: List[List[Int]]) {
          case (acc, list) => {
            (0 to (n - 1)).foldLeft(acc)((tmpResult, l) => {
              (l :: list) :: tmpResult
            })
          }
        }
    }
  }

  def readStartEndBlockLines(lines: List[String], tmp: List[String] = Nil): List[List[String]] = {
    lines match {
      case Nil             => Nil
      case "start" :: tail => readStartEndBlockLines(tail)
      case "end" :: tail   => tmp.reverse :: readStartEndBlockLines(tail)
      case head :: tail => {
        val partial = head :: tmp
        readStartEndBlockLines(tail, partial)
      }
    }
  }
}
