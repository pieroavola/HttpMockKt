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
  implementation("org.springframework:spring-web:6.1.12")
  implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.28")
  implementation(kotlin("reflect"))
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}
