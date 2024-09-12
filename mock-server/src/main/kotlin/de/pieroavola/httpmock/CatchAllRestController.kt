package de.pieroavola.httpmock

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.bind.annotation.RestController
import de.pieroavola.httpmock.requestdsl.mockRequest
import de.pieroavola.httpmock.requestdsl.response
import org.slf4j.LoggerFactory

@RestController
class CatchAllRestController {

  private val logger = LoggerFactory.getLogger(CatchAllRestController::class.java)!!

  @RequestMapping("/**", method = [GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE])
  fun request(request: HttpServletRequest, response: HttpServletResponse) {

    if (request.servletPath != "/favicon.ico") {
      logger.info("{} {}{}", request.method, request.servletPath, request.queryStringWithSeparator)
    }

    when (resolve(request.method)) {
      GET -> mockRequest.performGetRequest(request, response)
      HEAD -> mockRequest.performHeadRequest(request, response)
      POST -> mockRequest.performPostRequest(request, response)
      PUT -> mockRequest.performPutRequest(request, response)
      PATCH -> mockRequest.performPatchRequest(request, response)
      DELETE -> mockRequest.performDeleteRequest(request, response)
      OPTIONS -> mockRequest.performOptionRequest(request, response)
      TRACE -> mockRequest.performTraceRequest(request, response)
      else -> unknownMethod(request.method, response)
    }
  }

  private val mockRequest = mockRequest {
    get {
      response {
        status = HttpStatus.OK
        body = text("success")
      }
    }
  }

  private fun unknownMethod(method: String?, response: HttpServletResponse) {
    logger.error("HTTP method {} unknown", method)
    response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value())
  }

  private val HttpServletRequest.queryStringWithSeparator: String
    get() =
      if (queryString == null || queryString.isBlank()) ""
      else "?$queryString"
}
