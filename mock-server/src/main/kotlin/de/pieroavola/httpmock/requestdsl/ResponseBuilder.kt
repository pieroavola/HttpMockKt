package de.pieroavola.httpmock.requestdsl

import org.springframework.http.HttpStatus

@DslMarker
annotation class ResponseDsl

interface ResponseBuilderScope {
  fun text(string: String): ByteArray
}

class ResponseBuilder(
  var status: HttpStatus = HttpStatus.METHOD_NOT_ALLOWED,
  var body: ByteArray? = null,
) : ResponseBuilderScope {

  @ResponseDsl
  override fun text(string: String): ByteArray = string.toByteArray()

  fun build() = Response(
    status,
    body
  )
}

@ResponseDsl
fun response(block: ResponseBuilder.() -> Unit): Response = ResponseBuilder().apply(block).build()
