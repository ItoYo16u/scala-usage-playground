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

  def permutation[T](l: List[T]) : List[List[T]]= {
    l match {
      case Nil => return List(List())
      case head::Nil => return List(List(head))
      case head::tail::Nil => return List(List(head,tail),List(tail,head))
      case _ => l.foldRight(Nil:List[List[T]])((elm,acc)=>{
        permutation(l.filterNot(_==elm)).map((sub)=> {elm :: sub})
          .foldRight(acc)((list,result)=>{
            list :: result
          })
        }
      )
    }
  }
  def binomialPermutation(digit:Int):List[String]={
    digit match {
      case 1 => List("1","0")
      case _ => binomialPermutation(digit - 1)
        .foldRight(Nil:List[String])((z,acc)=>{
          ("0"+z) :: ("1"+z) :: acc
        })
    }
  }
}