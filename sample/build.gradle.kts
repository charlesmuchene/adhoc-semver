plugins {
    application
    kotlin("jvm") version "2.0.0"
    id("com.charlesmuchene.adhoc.semver")
}

group = "com.charlesmuchene.tools.sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "com.charlesmuchene.tools.App"
}

adhocSemVer {
//    versionFile.set(layout.projectDirectory.file("gradle.properties"))
}