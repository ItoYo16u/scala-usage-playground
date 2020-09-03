package web.akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object WebApp {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty,"webApp")
    implicit val executionContext:ExecutionContext = system.executionContext

    val requestHandler : HttpRequest => HttpResponse = {
            // uri,path,header,entity,protocol
      case HttpRequest(GET,Uri.Path("/"),_,_,_)=>HttpResponse(
        status = StatusCodes.OK,
        entity = HttpEntity.apply(
          contentType=ContentTypes.`application/json`,
          string = "{'status':'success'}"
        )
      )
      case r: HttpRequest => HttpResponse(404)
        // これが無いとInternalServerErrorになる
    }
    val bindingFuture = Http().newServerAt("localhost",8080).bindSync(requestHandler)
    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_=>system.terminate())
  }
}