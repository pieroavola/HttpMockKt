package de.pieroavola.httpmock.requestdsl

interface HttpRequestScope {

  fun get(block: Request.() -> Response)
  fun head(block: Request.() -> Response)
  fun post(block: Request.() -> Response)
  fun put(block: Request.() -> Response)
  fun patch(block: Request.() -> Response)
  fun delete(block: Request.() -> Response)
  fun option(block: Request.() -> Response)
  fun trace(block: Request.() -> Response)
}
