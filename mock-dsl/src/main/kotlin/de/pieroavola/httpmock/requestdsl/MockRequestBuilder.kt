package de.pieroavola.httpmock.requestdsl

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@HttpMockDsl
class MockRequestBuilder : HttpRequestScope {

  private var getFunction: Request.() -> Response = { response {} }
  private var headFunction: Request.() -> Response = { response {} }
  private var postFunction: Request.() -> Response = { response {} }
  private var putFunction: Request.() -> Response = { response {} }
  private var patchFunction: Request.() -> Response = { response {} }
  private var deleteFunction: Request.() -> Response = { response {} }
  private var optionFunction: Request.() -> Response = { response {} }
  private var traceFunction: Request.() -> Response = { response {} }

  //<editor-fold desc="HttpRequestScope">

  @HttpMockDsl
  override fun get(block: Request.() -> Response) {
    getFunction = block
  }

  @HttpMockDsl
  override fun head(block: Request.() -> Response) {
    headFunction = block
  }

  @HttpMockDsl
  override fun post(block: Request.() -> Response) {
    postFunction = block
  }

  @HttpMockDsl
  override fun put(block: Request.() -> Response) {
    putFunction = block
  }

  @HttpMockDsl
  override fun patch(block: Request.() -> Response) {
    patchFunction = block
  }

  @HttpMockDsl
  override fun delete(block: Request.() -> Response) {
    deleteFunction = block
  }

  @HttpMockDsl
  override fun option(block: Request.() -> Response) {
    optionFunction = block
  }

  @HttpMockDsl
  override fun trace(block: Request.() -> Response) {
    traceFunction = block
  }

  //</editor-fold>

  //<editor-fold desc="Perform Requests">

  fun performGetRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = getFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performHeadRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = headFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performPostRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = postFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performPutRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = putFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performPatchRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = patchFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performDeleteRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = deleteFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performOptionRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = optionFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  fun performTraceRequest(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse) {
    val response = traceFunction(servletRequest.asRequest)
    response.perform(servletResponse)
  }

  //</editor-fold>
}

@HttpMockDsl
fun mockRequest(block: MockRequestBuilder.() -> Unit): MockRequestBuilder = MockRequestBuilder().apply(block)

private val HttpServletRequest.asRequest: Request
  get() = Request(servletPath, queryStringToMap(queryString))

private fun queryStringToMap(queryString: String?): Map<String, String> {

  if (queryString.isNullOrBlank()) {
    return emptyMap()
  }

  return queryString.split("&")
    .associate { parameter -> parameter.split("=").let { it[0] to it[1] } }
}
