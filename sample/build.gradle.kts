import com.charlesmuchene.semver.BumpType

plugins {
    application
    id("maven-publish")
    kotlin("jvm") version "2.0.0"
    id("com.charlesmuchene.temp.semver.bump")
}

tempSemVerBump {
    versionFile = layout.projectDirectory.file("gradle.properties")
    targetTaskName = "publishToMavenLocal"
    bumpType = BumpType.MINOR
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
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