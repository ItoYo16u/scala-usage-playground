package web.akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http

import scala.concurrent.duration._
import akka.stream.scaladsl._
import akka.http.scaladsl.server.directives._
import akka.http.javadsl.model.ws.Message
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage, WebSocketUpgrade}
import akka.stream.KillSwitches
import akka.util.ByteString

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future, duration}
import scala.io.StdIn
import scala.util.Random

object WebApp {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty,"webApp")
    implicit val executionContext:ExecutionContext = system.executionContext

    val numbers = Source.fromIterator(()=> Iterator.continually(Random.nextInt()))
    val (sink,source) = MergeHub.source[Message].map(req => req).toMat(BroadcastHub.sink[Message])(Keep.both).run()
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

      case req @ HttpRequest(GET, Uri.Path("/join"), _, _,_) =>
        req.attribute(AttributeKeys.webSocketUpgrade) match {
            // プロトコルの更新
          case Some(upgrade: WebSocketUpgrade) => upgrade.handleMessages{
            val queryString = req.uri.rawQueryString.getOrElse("nothing")
            println(queryString)
            Flow.fromSinkAndSource(sink,source).mapConcat{
              case tm:TextMessage =>   tm :: tm :: Nil
              case bm: BinaryMessage => bm.dataStream.runWith(Sink.ignore)
                Nil
            }.throttle(1,1.seconds)
        /*  Flow[Message].mapConcat{
              case tm: TextMessage => (0 to 100).map(_ => tm)
              //TextMessage(Source.single(("response:")) ++ tm.textStream) :: Nil
              case bm: BinaryMessage =>
                // ignore binary messages but drain content to avoid the stream being clogged
                bm.dataStream.runWith(Sink.ignore)
                Nil
            }.throttle(1,1.seconds)*/
          }
          case None => HttpResponse(400,entity="invalid websocket request")
        }
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

