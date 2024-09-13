import de.pieroavola.httpmock.requestdsl.ResponseBuilder.response
import org.springframework.http.HttpStatus

System.out.println(this::class.qualifiedName)

get {

  println("GET")

  response {
    status = HttpStatus.OK
    body = text("success")
  }
}

post {

  if (path.contains("/abc")) {
    response { status = HttpStatus.NOT_FOUND }
  } else {
    response { status = HttpStatus.NO_CONTENT }
  }
}
