package com.charlesmuchene.semver.tasks

import net.swiftzer.semver.SemVer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class SetSemVerTask : SemVerTask() {

    @get:Input
    abstract val incomingVersion: Property<SemVer>

    override fun setVersion(version: SemVer): SemVer = this.incomingVersion.get()
}