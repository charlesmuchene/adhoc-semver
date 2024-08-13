package com.charlesmuchene.semver

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface SemVerBumpExtension {
    val bumpType: Property<BumpType>
    val versionFile: RegularFileProperty
    val targetTaskName: Property<String>
}