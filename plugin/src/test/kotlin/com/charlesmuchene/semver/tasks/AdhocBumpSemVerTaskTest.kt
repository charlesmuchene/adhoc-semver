package com.charlesmuchene.semver.tasks

import com.charlesmuchene.semver.AdhocSemVerExtension
import net.swiftzer.semver.SemVer
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertEquals

class AdhocBumpSemVerTaskTest {

    @TempDir
    private lateinit var tempDir: File

    private val project = ProjectBuilder.builder().build()
    private val task = project.tasks.create("bump", AdhocBumpSemVerTask::class.java)

    private lateinit var file: File

    @BeforeEach
    fun setup() {
        file = File(tempDir, "gradle.properties").apply {
            writeText("version=2.13.7")
        }
        task.versionFile.set(file)
    }

    @Test
    fun `upgrade task bumps major version given major type`() {
        task.bumpType.set(AdhocSemVerExtension.BumpType.MAJOR)
        task.execute()

        val version = task.readVersion(task.readProperties()).second

        assertEquals(SemVer(3), version)
    }

    @Test
    fun `upgrade task bumps minor version given major type`() {
        task.bumpType.set(AdhocSemVerExtension.BumpType.MINOR)
        task.execute()

        val version = task.readVersion(task.readProperties()).second

        assertEquals(SemVer(2, 14, 0), version)
    }

    @Test
    fun `upgrade task bumps patch version given major type`() {
        task.bumpType.set(AdhocSemVerExtension.BumpType.PATCH)
        task.execute()

        val version = task.readVersion(task.readProperties()).second

        assertEquals(SemVer(2, 13, 8), version)
    }
}