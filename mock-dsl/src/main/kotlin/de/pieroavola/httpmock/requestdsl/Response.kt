package de.pieroavola.httpmock.requestdsl

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus

class Response(
  private val status: HttpStatus = HttpStatus.METHOD_NOT_ALLOWED,
  private val body: ByteArray? = null,
) {

  fun perform(servletResponse: HttpServletResponse) {

    servletResponse.status = status.value()

    servletResponse.outputStream.use {
      body?.run { it.write(this) }
    }
  }
}
