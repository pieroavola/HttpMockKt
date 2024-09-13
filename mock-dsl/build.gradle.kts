plugins {
  kotlin("jvm") version "1.9.25"
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {

  api("org.springframework:spring-web:6.1.12")
  implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.28")
  implementation(kotlin("reflect"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

  implementation("org.jetbrains.kotlin:kotlin-scripting-common")
  implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
  implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
  implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}
