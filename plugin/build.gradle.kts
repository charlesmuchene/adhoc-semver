plugins {
    id("java-gradle-plugin")
    kotlin("jvm") version libs.versions.kotlin
}

repositories {
    mavenCentral()
}

group = "com.charlesmuchene.adhoc.semver"
version = "0.1.0"

dependencies {
    implementation(libs.semver)
//    implementation("net.swiftzer.semver:semver:2.0.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("adhocsemver") {
            id = "com.charlesmuchene.adhoc.semver"
            implementationClass = "com.charlesmuchene.semver.AdhocSemVerPlugin"
        }
    }
}