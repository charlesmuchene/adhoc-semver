package com.charlesmuchene.semver

import com.charlesmuchene.semver.tasks.SemVerBumpTask
import com.charlesmuchene.semver.tasks.SetSemVerTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class SemVerBumpPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION, SemVerBumpExtension::class.java).apply {
            bumpType.convention(BumpType.PATCH)
            shouldRevertVersionAfterExecution.convention(true)
            versionFile.convention(target.layout.projectDirectory.file(DEFAULT_VERSION_FILE))
        }

        val semVerBumpTask = target.tasks.register(BUMP_TASK, SemVerBumpTask::class.java) {
            it.bumpType.convention(extension.bumpType)
            it.versionFile.convention(extension.versionFile)
        }

        // TODO Make this depend on the publish plugin/task
        val targetTask = target.tasks.named("run").map { it.dependsOn(semVerBumpTask) }

        if (extension.shouldRevertVersionAfterExecution.get()) {
            val semVerSetTask = target.tasks.register(SET_TASK, SetSemVerTask::class.java) { semVerTask ->
                semVerTask.versionFile.convention(extension.versionFile)
                semVerTask.incomingVersion.convention(semVerBumpTask.flatMap { task -> task.outputVersion })
            }
            targetTask.map { it.finalizedBy(semVerSetTask) }
        }
    }

    companion object {
        const val EXTENSION = "tempSemVerBump"
        const val SET_TASK = "semVerSetVersion"
        const val BUMP_TASK = "semVerBump"
        const val DEFAULT_VERSION_FILE = "gradle.properties"
    }
}