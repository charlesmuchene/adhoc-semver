package com.charlesmuchene.semver

import com.charlesmuchene.semver.tasks.AdhocBumpSemVerTask
import com.charlesmuchene.semver.tasks.AdhocSetSemVerTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AdhocSemVerPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("adhocSemVer", AdhocSemVerExtension::class.java).apply {
            bumpType.convention(AdhocSemVerExtension.BumpType.PATCH)
            versionFile.convention(target.layout.projectDirectory.file("gradle.properties"))
            shouldRevertAfter.convention(true)
        }

        val adhocBumpSemVerTask = target.tasks.register("adhocBumpSemVer", AdhocBumpSemVerTask::class.java) {
            it.bumpType.convention(extension.bumpType)
            it.versionFile.convention(extension.versionFile)
        }

        // TODO Make this depend on the publish plugin/task
        val targetTask = target.tasks.named("run").get()
        targetTask.dependsOn(adhocBumpSemVerTask)

        if (extension.shouldRevertAfter.get()) {
            targetTask.finalizedBy(target.tasks.register("adhocSetSemVer", AdhocSetSemVerTask::class.java) {
                it.versionFile.convention(extension.versionFile)
                it.incomingVersion.convention(adhocBumpSemVerTask.get().outputVersion)
            })
        }
    }
}