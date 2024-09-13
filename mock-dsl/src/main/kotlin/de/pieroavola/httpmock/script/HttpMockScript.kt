package de.pieroavola.httpmock.script

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlinx.coroutines.runBlocking
import de.pieroavola.httpmock.requestdsl.HttpRequestScope
import de.pieroavola.httpmock.requestdsl.MockRequestBuilder

@KotlinScript(
  fileExtension = "mock.kts",
  compilationConfiguration = HttpMockScriptConfiguration::class
)
//abstract class HttpMockScript : (MockRequestBuilder.() -> Unit) -> Unit
abstract class HttpMockScript : HttpRequestScope

object HttpMockScriptConfiguration : ScriptCompilationConfiguration({

  defaultImports(
    DependsOn::class.qualifiedName!!,
    Repository::class.qualifiedName!!,
    "de.pieroavola.httpmock.requestdsl.ResponseBuilder.response",
  )

  jvm {
    dependenciesFromCurrentContext(wholeClasspath = true)
  }

  refineConfiguration {
    onAnnotations(DependsOn::class, Repository::class, handler = ::configureMavenDepsOnAnnotations)
  }

  baseClass(HttpRequestScope::class)
}) {

  private fun readResolve(): Any = HttpMockScriptConfiguration
}

private fun configureMavenDepsOnAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
  val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
    ?: return context.compilationConfiguration.asSuccess()
  return runBlocking {
    resolver.resolveFromScriptSourceAnnotations(annotations)
  }.onSuccess {
    context.compilationConfiguration.with {
      dependencies.append(JvmDependency(it))
    }.asSuccess()
  }
}

private val resolver = CompoundDependenciesResolver(FileSystemDependenciesResolver(), MavenDependenciesResolver())
