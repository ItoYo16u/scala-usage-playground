package web.akka

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}
import pdi.jwt.Jwt
import pdi.jwt.JwtAlgorithm.RS256

import java.io.ByteArrayInputStream
import java.security.PublicKey
import java.security.cert.CertificateFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object JWTDirectives {

  def validateJWTToken: AuthenticationDirective[String] = {
    authenticateOAuth2Async(
      "token_required",
      authenticator
    )
  }
  def fetchPublicKey():Future[PublicKey] = Future  {
    val cert = "-----BEGIN CERTIFICATE-----\nYOUR_KEY\n-----END CERTIFICATE-----\n"
    CertificateFactory
      .getInstance("X.509")
      .generateCertificate(new ByteArrayInputStream(cert.getBytes))
      .getPublicKey
  }

  def validateJWT(credentials: Credentials,pubKey: PublicKey) : Option[String] = credentials match {
      case Credentials.Provided(jwt) =>Jwt.decode(jwt, pubKey, Seq(RS256)).toOption
      case _ => None
    }

  private def authenticator: Credentials => Future[Option[String]]= {
    extractedToken => fetchPublicKey().map{
      pubKey=> validateJWT(extractedToken,pubKey)
    }.recover{case _ => None}
  }
}
