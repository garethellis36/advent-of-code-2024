plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.garethellis.adventofcode.twentyfour"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.ajalt.clikt:clikt:5.0.1")
}

tasks.test {
    useJUnitPlatform()
}
