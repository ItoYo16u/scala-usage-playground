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
    if(l.length==2){
      return List(List(l.head,l.last),List(l.last,l.head))
    }
    l.foldRight(Nil:List[List[T]])((elm,acc)=>{
      permutation(l.filterNot(_==elm)).map((sub)=> {elm :: sub})
        .foldRight(acc)((list,result)=>{
          list :: result
        })

    })
  }
}