import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Free_emitter entity.
 */
class Free_emitterGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connection("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-CSRF-TOKEN" -> "${csrf_token}"
    )

    val scn = scenario("Test the Free_emitter entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token")))
        .pause(10)
        .repeat(2) {
            exec(http("Get all free_emitters")
            .get("/api/free-emitters")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new free_emitter")
            .post("/api/free-emitters")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "reference":"SAMPLE_TEXT", "num_int":"SAMPLE_TEXT", "num_ext":"SAMPLE_TEXT", "street":"SAMPLE_TEXT", "create_date":"2020-01-01T00:00:00.000Z", "activated":null, "rfc":"SAMPLE_TEXT", "email":"SAMPLE_TEXT", "bussines_name":"SAMPLE_TEXT", "location":"SAMPLE_TEXT", "intersection":"SAMPLE_TEXT", "fax":"SAMPLE_TEXT", "phone1":"SAMPLE_TEXT", "phone2":"SAMPLE_TEXT", "path_certificate":"SAMPLE_TEXT", "path_key":"SAMPLE_TEXT", "path_logo":"SAMPLE_TEXT", "filecertificate":null, "filekey":null, "filelogo":null, "accuracy":"0", "valid_certificate":null, "info_certificate":"SAMPLE_TEXT", "date_certificate":"2020-01-01T00:00:00.000Z", "pass_certificate":"SAMPLE_TEXT", "rfc_certificate":"SAMPLE_TEXT", "bussines_name_cert":"SAMPLE_TEXT", "date_created_cert":"SAMPLE_TEXT", "date_expiration_cert":"SAMPLE_TEXT", "valid_days_cert":"SAMPLE_TEXT", "number_certificate":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_free_emitter_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created free_emitter")
                .get("${new_free_emitter_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created free_emitter")
            .delete("${new_free_emitter_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
