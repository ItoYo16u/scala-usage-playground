package web.akka

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route
import web.akka.JWTDirectives.validateJWTToken

object Routes {
  def route : Route = {
    get {
  path("jwt"){
    validateJWTToken {
      userId => ???
    }
  }
  }
  }
}
