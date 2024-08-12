package com.charlesmuchene.semver

import com.charlesmuchene.semver.tasks.BumpSemVerTask
import com.charlesmuchene.semver.tasks.SetSemVerTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AdhocSemVerPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION, AdhocSemVerExtension::class.java).apply {
            bumpType.convention(BumpType.PATCH)
            shouldRevertVersionAfterExecution.convention(true)
            versionFile.convention(target.layout.projectDirectory.file(DEFAULT_VERSION_FILE))
        }

        val bumpSemVerTask = target.tasks.register(BUMP_TASK, BumpSemVerTask::class.java) {
            it.bumpType.convention(extension.bumpType)
            it.versionFile.convention(extension.versionFile)
        }

        // TODO Make this depend on the publish plugin/task
        val targetTask = target.tasks.named("run").get().apply { dependsOn(bumpSemVerTask) }

        if (extension.shouldRevertVersionAfterExecution.get()) {
            targetTask.finalizedBy(target.tasks.register(SET_TASK, SetSemVerTask::class.java) {
                it.versionFile.convention(extension.versionFile)
                it.incomingVersion.convention(bumpSemVerTask.get().outputVersion)
            })
        }
    }

    companion object {
        const val EXTENSION = "adhocSemVer"
        const val SET_TASK = "adhocSetSemVer"
        const val BUMP_TASK = "adhocBumpSemVer"
        const val DEFAULT_VERSION_FILE = "gradle.properties"
    }
}