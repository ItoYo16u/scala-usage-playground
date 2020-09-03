package web.akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.stream.scaladsl._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn
import scala.util.Random

object WebApp {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty,"webApp")
    implicit val executionContext:ExecutionContext = system.executionContext
    val numbers = Source.fromIterator(()=> Iterator.continually(Random.nextInt()))
    val requestHandler : HttpRequest => HttpResponse = {
            // uri,path,header,entity,protocol
      case HttpRequest(GET,Uri.Path("/"),_,_,_)=>HttpResponse(
        status = StatusCodes.OK,
        entity = HttpEntity.apply(
          contentType=ContentTypes.`application/json`,
          string = "{'status':'success'}"
        )
      )
      case HttpRequest(GET,Uri.Path("/random"),_,_,_) => HttpResponse(
        entity = HttpEntity.apply(
          contentType = ContentTypes.`text/plain(UTF-8)`,
          numbers.map(n=>ByteString(s"$n\n"))
        )
      )
      case r: HttpRequest =>
        r.discardEntityBytes()
        HttpResponse(404)
        // これが無いとInternalServerErrorになる
    }
    val bindingFuture = Http().newServerAt("localhost",8080).bindSync(requestHandler)
    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_=>system.terminate())
  }
}