plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.3.3"
  id("io.spring.dependency-management") version "1.1.6"
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

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  implementation("org.jetbrains.kotlin:kotlin-scripting-common")
  implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
  implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
  implementation(project(":mock-dsl"))

  developmentOnly("org.springframework.boot:spring-boot-devtools")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}
