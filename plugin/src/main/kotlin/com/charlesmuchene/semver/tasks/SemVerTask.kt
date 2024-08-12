package com.charlesmuchene.semver.tasks

import com.charlesmuchene.semver.exceptions.InvalidInputFileException
import com.charlesmuchene.semver.exceptions.MissingVersionException
import net.swiftzer.semver.SemVer
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.Writer
import java.util.*

abstract class SemVerTask : DefaultTask() {

    @get:InputFile
    abstract val versionFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val properties = readProperties()
        val (key, semver) = readVersion(properties)
        val version = setVersion(version = semver)
        properties.setProperty(key, version.toString())
        writeProperties(properties)
    }

    abstract fun setVersion(version: SemVer): SemVer

    @Throws(MissingVersionException::class)
    internal fun readVersion(properties: Properties): Pair<String, SemVer> = properties.entries.mapNotNull { entry ->
        val key = entry.key as? String ?: return@mapNotNull null
        val semver = semVer((entry.value as? String).orEmpty()) ?: return@mapNotNull null
        key to semver
    }.firstOrNull() ?: throw MissingVersionException(versionFile.get().asFile.name)

    internal fun semVer(value: String): SemVer? = SemVer.parseOrNull(value)

    @Throws(InvalidInputFileException::class)
    internal fun readProperties(): Properties {
        val inputFile = versionFile.get().asFile
        if (!inputFile.exists()) throw InvalidInputFileException(inputFile.name)

        return inputFile.bufferedReader().use {
            Properties().apply { load(it) }
        }
    }

    internal fun writeProperties(properties: Properties, writer: Writer = versionFile.get().asFile.writer()) {
        properties.store(writer, null)
        writer.close()
    }
}