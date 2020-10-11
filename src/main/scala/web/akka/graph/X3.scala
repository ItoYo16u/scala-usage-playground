package web.akka.graph

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Flow, Sink, Source }

// Flowはフィルター, Sinkは入り口,Sourceは出口,の抽象化

object X3 {
  implicit val actorSystem = ActorSystem()
  implicit val mat         = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val source = Source(1 to 100)
    val flow   = Flow.fromFunction[Int, Int](_ * 3)
    val sink   = Sink.foreach(println)
    val graph  = source.via(flow).to(sink)
    graph.run()
  }
}
