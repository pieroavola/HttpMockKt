package de.pieroavola.httpmock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HttpMockApplication

fun main(args: Array<String>) {
  runApplication<HttpMockApplication>(*args)
}
