pluginManagement {
    includeBuild("../plugin")
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "temp-semver-bump-sample"
