package web.akka
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.AuthenticationDirective
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSpecLike, Matchers}

class JWTDirectivesSpec extends  FunSpecLike with Matchers with ScalatestRouteTest with ScalaFutures  {
  private val jwtDirective: AuthenticationDirective[String] = JWTDirectives.validateJWTToken
  private val route = Route.seal{
      path(""){
        jwtDirective{
          userId => complete(userId)
        }
      }
  }
  describe("Get"){
      describe("without token "){
        it("returns 401 Unauthorized"){
          val request = Get("/")
          request ~> route ~> check {
            status shouldEqual StatusCodes.Unauthorized
            responseAs[String] shouldEqual "The resource requires authentication, which was not supplied with the request"
          }
        }
        describe("with invalid token"){
          it("returns 401 Unauthorized"){
            val token = "invalid_token"

            val request = Get("/").addCredentials(OAuth2BearerToken(token))

            request ~> route ~> check {
              status shouldEqual StatusCodes.Unauthorized
              responseAs[String] shouldEqual "The supplied authentication is invalid"
            }
          }
        }
        describe("with valid token"){
          it("returns userId"){
            val token = "YOUR_TOKEN_HERE"
            val request = Get("/").addCredentials(OAuth2BearerToken(token))
            request ~> route ~> check {
              status shouldEqual StatusCodes.OK
            }
          }
        }
    }
  }
}