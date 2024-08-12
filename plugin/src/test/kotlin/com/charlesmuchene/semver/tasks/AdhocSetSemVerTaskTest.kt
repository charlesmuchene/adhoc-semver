package com.charlesmuchene.semver.tasks

import net.swiftzer.semver.SemVer
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class AdhocSetSemVerTaskTest {

    @TempDir
    private lateinit var tempDir: File

    private val project = ProjectBuilder.builder().build()
    private val task = project.tasks.create("downgrade", AdhocSetSemVerTask::class.java)

    private lateinit var file: File

    @BeforeEach
    fun setup() {
        file = File(tempDir, "gradle.properties").apply {
            writeText("version=3.2.10")
        }
        task.versionFile.set(file)
    }

    @Test
    fun `task sets given version to file`() {
        task.incomingVersion.set(SemVer(3, 4, 5))

        task.execute()

        val version = task.readVersion(task.readProperties()).second

        assertEquals("3.4.5", version.toString())
    }
}