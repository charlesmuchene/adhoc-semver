plugins {
    id("com.gradle.develocity") version("3.17.6")
}

develocity {
    buildScan {
        publishing.onlyIf { false }
        obfuscation {
            username { "mzeeiyah" }
            hostname { "hapa" }
            ipAddresses { listOf("mtaani") }
        }
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set(System.getenv("PUBLISH_BUILD_SCAN"))
    }
}

rootProject.name = "adhoc-semver"

include("sample")
