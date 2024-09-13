package de.pieroavola.httpmock

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import org.springframework.stereotype.Component
import de.pieroavola.httpmock.requestdsl.HttpRequestScope
import de.pieroavola.httpmock.requestdsl.MockRequestBuilder
import de.pieroavola.httpmock.script.HttpMockScript

@Component
class ScriptRunner {

  private val scriptPath = Paths.get("./scripts/http.mock.kts")

  fun runScript(): MockRequestBuilder {

    val builder = MockRequestBuilder()
    evaluateFile(scriptPath, builder).also { res->
      res.reports
        .filter { it.severity == ScriptDiagnostic.Severity.ERROR }
        .forEach { println(it) }
    }
    return builder
  }

  private fun evaluateFile(script: Path, scope: HttpRequestScope): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<HttpMockScript>()
    return BasicJvmScriptingHost().eval(
      script.toFile().toScriptSource(),
      compilationConfiguration,
      ScriptEvaluationConfiguration {
        providedProperties.append(mapOf("scope" to scope))
      }
    )
  }
}
