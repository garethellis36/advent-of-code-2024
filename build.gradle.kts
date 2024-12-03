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
}

tasks.test {
    useJUnitPlatform()
}
