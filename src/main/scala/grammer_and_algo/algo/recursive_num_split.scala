package grammer_and_algo.algo

object RecursiveMethod {
  def recursive_num_split(n: Int): Int = {
    val n_as_arr: Array[String] = n.toString.split("")
    val sum: Int = n_as_arr.foldLeft(0: Int)((acc, s) => {
      acc + s.toInt
    })
    if (sum > 10) {
      recursive_num_split(sum)
    } else {
      n + sum
    }
  }
}