import com.charlesmuchene.semver.BumpType

plugins {
    application
    kotlin("jvm") version "2.0.0"
    id("com.charlesmuchene.adhoc.semver")
}

adhocSemVer {
    versionFile = layout.projectDirectory.file("gradle.properties")
    shouldRevertVersionAfterExecution = true
    bumpType = BumpType.MAJOR
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