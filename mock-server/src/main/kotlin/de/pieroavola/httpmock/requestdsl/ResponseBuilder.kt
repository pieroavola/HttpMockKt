package de.pieroavola.httpmock.requestdsl

import org.springframework.http.HttpStatus

interface ResponseBuilderScope {
  fun text(string: String): ByteArray
}

class ResponseBuilder(
  var status: HttpStatus = HttpStatus.METHOD_NOT_ALLOWED,
  var body: ByteArray? = null,
) : ResponseBuilderScope {

  @HttpRequestMarker
  override fun text(string: String): ByteArray = string.toByteArray()

  fun build() = Response(
    status,
    body
  )
}

@HttpRequestMarker
fun response(block: ResponseBuilder.() -> Unit): Response = ResponseBuilder().apply(block).build()
