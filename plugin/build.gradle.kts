@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("jvm") version libs.versions.kotlin
    id("com.gradle.plugin-publish") version libs.versions.plugin.publish
}

repositories {
    mavenCentral()
}

group = "com.charlesmuchene.semver.bump"
version = providers.fileContents(
    layout.projectDirectory.file("gradle/version.txt")
).asText.getOrElse("")

dependencies {
    implementation(libs.semver)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("tempSemVerBump") {
            tags = listOf("semver", "gradle")
            displayName = "Temporary SemVer Bump"
            id = "com.charlesmuchene.temp.semver.bump"
            implementationClass = "com.charlesmuchene.semver.SemVerBumpPlugin"
            description = "A plugin that temporarily bumps and then reverts a project's version."
        }
    }
    vcsUrl = "https://github.com/charlesmuchene/temp-semver-bump"
    website = "https://github.com/charlesmuchene/temp-semver-bump"
}

publishing {
    repositories {
        mavenLocal()
    }
}