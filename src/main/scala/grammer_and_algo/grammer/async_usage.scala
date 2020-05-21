import scala.concurrent.{Future,Await}

import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.duration._
object AsyncUsage extends App {
  val start = System.currentTimeMillis()
  def info(msg:String):Unit = printf(
    "%.2f: %s\n",
    (System.currentTimeMillis() - start)/1000.0,msg
  )

  case class Product(name: String) {
    def assembleWith(that:Product):Product= Product(s"${this.name} with ${that.name}")
  }
  // this is sync usage
  def build(name:String):Product = {
    Thread.sleep(1000L)
    info(s"$name built!")
    Product(name)
  }

  def pack(product:Product): Unit = {
    info(s"(${product.name})is successfully packed!")
  }

  val p1 = build("p1")
  val p2 = build("p2")
  pack(p1 assembleWith p2)

  val badForWithFuture:Future[Unit] = for {
    badP1 <- Future {
      build("badP1")
    }
    badP2 <- Future {
      build("badP2")
    }
  } yield {
    pack(badP1 assembleWith badP2)
  }
  // this is bad for performance
  Await.result(badForWithFuture,10.seconds)
  // instead of this, write like bellow

  // You should make Instance outside "for"
  val betterP1 = Future {build("betterP1")}
  val betterP2 = Future {build("betterP2")}

  val betterForWIthFuture = for {
    b1 <- betterP1
    b2 <- betterP2
  } yield {
    pack(b1 assembleWith b2)
  }
  // this is better for performance
  Await.result(betterForWIthFuture,10.seconds)

  // you can use "async","await" to handle non blocking code with better readability
  // NOTE: async is external library.
  /* Using async & await,  you can write like below
       val result = async {
         val p1 = async { build("p1") }
         val p2 = async { build("p2") }
         pack(await(p1) + await(p2)) // NOTE: await[T](awaitable:Future[T]): T = ...
         //And You can write like bellow when inside async block !
         if(await(aMethodReturningFuture[Boolean])){
           val optionParts = async {build("optionParts")}
           pack(await(p1) assembly await(p2) assembly await(optionParts))
         }

       }
       Await.result(result,10.seconds)
   */



}