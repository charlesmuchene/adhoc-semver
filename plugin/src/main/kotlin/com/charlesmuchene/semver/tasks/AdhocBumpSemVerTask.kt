package com.charlesmuchene.semver.tasks

import com.charlesmuchene.semver.AdhocSemVerExtension
import com.charlesmuchene.semver.AdhocSemVerExtension.BumpType.MAJOR
import com.charlesmuchene.semver.AdhocSemVerExtension.BumpType.MINOR
import com.charlesmuchene.semver.AdhocSemVerExtension.BumpType.PATCH
import net.swiftzer.semver.SemVer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

abstract class AdhocBumpSemVerTask : AdhocSemVerTask() {

    @get:Input
    abstract val bumpType: Property<AdhocSemVerExtension.BumpType>

    @get:Internal
    abstract val outputVersion: Property<SemVer>

    override fun setVersion(version: SemVer): SemVer = when (bumpType.get()) {
        MAJOR -> version.nextMajor()
        MINOR -> version.nextMinor()
        PATCH -> version.nextPatch()
        else -> version
    }.also { outputVersion.set(version) }

}