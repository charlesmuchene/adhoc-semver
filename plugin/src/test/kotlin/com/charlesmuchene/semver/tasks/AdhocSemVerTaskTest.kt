package com.charlesmuchene.semver.tasks

import com.charlesmuchene.semver.exceptions.InvalidInputFileException
import com.charlesmuchene.semver.exceptions.MissingVersionException
import net.swiftzer.semver.SemVer
import org.gradle.api.file.RegularFileProperty
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.PipedReader
import java.io.PipedWriter
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class AdhocSemVerTaskTest {

    private val project = ProjectBuilder.builder().build()
    private val task = project.tasks.create("task", TestAdhocSemVerTask::class.java)

    @Test
    fun `sem ver parses version string`() {
        assertNull(task.semVer("invalid"))
        assertNull(task.semVer("a.b.c"))
        assertEquals(SemVer(0), task.semVer("0.0.0"))
    }

    @Test
    fun `read properties reads version in properties`() {
        task.setValidFile()

        val properties = task.readProperties()

        assertFalse { properties.isEmpty }
        val (_, version) = task.readVersion(properties)

        assertEquals("2.13.7", version.toString())
    }

    @Test
    fun `read properties reads first version only`() {
        task.setMultipleVersionsFile()

        val (_, version) = task.readVersion(task.readProperties())

        assertEquals(SemVer(1), version)
    }

    @Test
    fun `read properties throws for non existent input file`() {
        task.setNonExistentFile()

        assertThrows<InvalidInputFileException> {
            task.readProperties()
        }
    }

    @Test
    fun `read properties throws for empty file`() {
        task.setEmptyFile()

        assertThrows<MissingVersionException> {
            task.readVersion(task.readProperties())
        }
    }

    @Test
    fun `read properties throws for missing version in file`() {
        task.setNoVersionInFile()

        assertThrows<MissingVersionException> {
            task.readVersion(task.readProperties())
        }
    }

    @Test
    fun `read properties throws for non-property file`() {
        task.setNonPropertyFile()

        assertThrows<MissingVersionException> {
            task.readVersion(task.readProperties())
        }
    }

    @Test
    fun `write properties writes properties`() {
        val properties = Properties().apply {
            setProperty("version", "1.1.3")
        }
        val reader = PipedReader()
        val writer = PipedWriter(reader)

        task.writeProperties(properties, writer)

        val (_, version) = task.readVersion(Properties().apply {
            load(reader)
        })

        assertEquals(SemVer(1, 1, 3), version)
    }

    @Test
    fun `executing adhoc test task yields the same version`() {
        task.setValidInputs()

        task.execute()

        val (_, version) = task.readVersion(task.readProperties())
        assertEquals(SemVer(2, 13, 7), version)
    }

    internal abstract class TestAdhocSemVerTask : AdhocSemVerTask() {
        override val versionFile: RegularFileProperty = project.objects.fileProperty()

        override fun setVersion(version: SemVer): SemVer = version

        private fun setFile(name: String) {
            versionFile.set(File(javaClass.getResource("/$name")!!.toURI()))
        }

        fun setValidFile() {
            setFile("gradle.properties")
        }

        fun setEmptyFile() {
            setFile("empty.properties")
        }

        fun setNoVersionInFile() {
            setFile("no-version.properties")
        }

        fun setNonPropertyFile() {
            setFile("not-a-properties-file")
        }

        fun setNonExistentFile() {
            versionFile.set(File("non-existent-file"))
        }

        fun setMultipleVersionsFile() {
            setFile("multiple-versions.properties")
        }

        fun setValidInputs() {
            setValidFile()
        }
    }
}