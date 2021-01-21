package web.akka

import java.io.File

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.concurrent.duration._
import akka.stream.scaladsl._

import scala.collection.immutable.Seq
import akka.http.javadsl.model.ws.Message
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{ headers, _ }
import akka.http.scaladsl.model.headers.{ ContentDispositionTypes, RangeUnits, RawHeader }
import akka.http.scaladsl.model.ws.{ BinaryMessage, TextMessage, WebSocketUpgrade }
import akka.stream.KillSwitches
import akka.util.ByteString

import scala.concurrent.duration.Duration
import scala.concurrent.{ duration, ExecutionContext, Future }
import scala.io.StdIn
import scala.util.Random

object WebApp {

  def main(args: Array[String]): Unit = {
    // actor treeのroot
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "webApp")
    // thread poolとタスクの管理
    implicit val executionContext: ExecutionContext = system.executionContext

    val numbers = Source.fromIterator(() => Iterator.continually(Random.nextInt()))
    // akka graphの生成. streamのmerge, broadcastに使う
    val (sink, source) = MergeHub.source[Message].map(req => req).toMat(BroadcastHub.sink[Message])(Keep.both).run()
    // 以下の記述はakkaの低レベルAPIを使った書き方
    // Route DSLを使って書くほうが良い
    val requestHandler: HttpRequest => HttpResponse = {
      // uri,path,header,entity,protocol
      case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
        HttpResponse(
          status = StatusCodes.OK,
          entity = HttpEntity.apply(
            contentType = ContentTypes.`application/json`,
            string = "{'status':'success'}"
          )
        )
        // 低レベルapiでvideoのchunk配信
        // 参考: https://medium.com/@awesomeorji/video-streaming-with-akka-http-9e95985bfe86
      case req @ HttpRequest(GET, Uri.Path("/video"), _, _, _) =>
        req.headers.find(_.is("range")).map(_.value()) match {
          case None => HttpResponse(status = StatusCodes.RangeNotSatisfiable)
          case Some(range) =>
            val file   = new File("")
            val fileSize = file.length
            val _range = range.split("=")(1).split("-")
            val start  = _range(0).toInt
            val end = if (_range.length > 1) {
              _range(1).toLong
            } else {
              fileSize - 1
            }
            HttpResponse(
              status = StatusCodes.PartialContent,
              headers = Seq(
                headers.`Content-Disposition`(
                  dispositionType = ContentDispositionTypes.attachment,
                  params = Map("filename" -> "movie.mp4")
                ),
                headers.`Content-Range`
                  .apply(rangeUnit = RangeUnits.Bytes, contentRange = ContentRange(first = start/fileSize, last = end/fileSize)),
                headers.`Accept-Ranges`.apply(rangeUnits = Seq(RangeUnits.Bytes))
              ),
              entity = HttpEntity.apply(MediaTypes.`video/mp4`, FileIO.fromPath(file.toPath, 8192, start.toInt))
            )
        }

      case HttpRequest(GET, Uri.Path("/random"), _, _, _) =>
        HttpResponse(
          entity = HttpEntity.apply(
            contentType = ContentTypes.`text/plain(UTF-8)`,
            numbers.map(n => ByteString(s"$n\n"))
          )
        )

      case req @ HttpRequest(GET, Uri.Path("/join"), _, _, _) =>
        req.attribute(AttributeKeys.webSocketUpgrade) match {
          // プロトコルの更新
          case Some(upgrade: WebSocketUpgrade) =>
            upgrade.handleMessages {
              val queryString = req.uri.rawQueryString.getOrElse("nothing")
              println(queryString)
              Flow
                .fromSinkAndSource(sink, source).mapConcat {
                  case tm: TextMessage => tm :: tm :: Nil
                  case bm: BinaryMessage =>
                    bm.dataStream.runWith(Sink.ignore)
                    Nil
                }.throttle(1, 1.seconds)
              /*  Flow[Message].mapConcat{
                  case tm: TextMessage => (0 to 100).map(_ => tm)
                  //TextMessage(Source.single(("response:")) ++ tm.textStream) :: Nil
                  case bm: BinaryMessage =>
                    // ignore binary messages but drain content to avoid the stream being clogged
                    bm.dataStream.runWith(Sink.ignore)
                    Nil
                }.throttle(1,1.seconds)*/
            }
          case None => HttpResponse(400, entity = "invalid websocket request")
        }
      case r: HttpRequest =>
        r.discardEntityBytes()
        HttpResponse(404)
      // これが無いとInternalServerErrorになる
    }
    val bindingFuture = Http().newServerAt("localhost", 8080).bindSync(requestHandler)
    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
