plugins {
  kotlin("jvm") version "1.9.25"
}

group = "de.pieroavola"

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":mock-dsl"))
}
