package de.pieroavola.httpmock.requestdsl

data class Request(
  val path: String,
  val parameters: Map<String, String>
)
