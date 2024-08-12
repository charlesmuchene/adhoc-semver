package com.charlesmuchene.semver.tasks

import com.charlesmuchene.semver.BumpType.MAJOR
import com.charlesmuchene.semver.BumpType.MINOR
import com.charlesmuchene.semver.BumpType.PATCH
import com.charlesmuchene.semver.BumpType
import net.swiftzer.semver.SemVer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

abstract class BumpSemVerTask : SemVerTask() {

    @get:Input
    abstract val bumpType: Property<BumpType>

    @get:Internal
    abstract val outputVersion: Property<SemVer>

    override fun setVersion(version: SemVer): SemVer = when (bumpType.get()) {
        MAJOR -> version.nextMajor()
        MINOR -> version.nextMinor()
        PATCH -> version.nextPatch()
        else -> version
    }.also { outputVersion.set(version) }

}