package io.razza.pcboxmanager

import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.io.InputStreamReader

/**
 * Class to build 86Box command-line arguments.
 */
class EightySixBox {
    val program: String
    val arguments: List<String>

    constructor() {
        this.program = "86Box"
        this.arguments = listOf()
    }

    constructor(program: String, arguments: List<String> = listOf()) {
        this.program = program
        this.arguments = arguments
    }

    /**
     * Sets the configuration file 86box uses.
     */
    fun withConfig(path: String) = EightySixBox(program, arguments + "--config" + path)

    /**
     * Starts 86box in full screen mode.
     */
    fun withFullscreen() = EightySixBox(program, arguments + "--fullscreen")

    /**
     * Mounts a floppy disk image on a drive letter.
     */
    fun withImage(driveLetter: Char, path: String) = EightySixBox(program, arguments + "--image" + "$driveLetter:$path")

    /**
     * Sets the log file 86box writes into.
     */
    fun withLogFile(path: String) = EightySixBox(program, arguments + "--logfile" + path)

    fun withNoConfirm() = EightySixBox(program, arguments + "--noconfirm")

    fun withDumpConfig() = EightySixBox(program, arguments + "--dumpcfg")

    fun withVmPath(path: String) = EightySixBox(program, arguments + "--vmpath" + path)

    fun withRomPath(path: String) = EightySixBox(program, arguments + "--rompath" + path)

    fun withSettingsOnly() = EightySixBox(program, arguments + "--settings")

    fun withVmName(name: String) = EightySixBox(program, arguments + "--vmname" + name)

    /**
     * Starts 86box.
     */
    fun start() = ProcessBuilder(program, *arguments.toTypedArray()).start()!!

    /**
     * Checks if the given path is something resembling 86box
     */
    fun validate(): Boolean {
        val process = try {
            ProcessBuilder(program, "--help").start()
        } catch (e: IOException) {
            return false
        }
        val stdout = buildString {
            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                reader.forEachLine { line ->
                    append(line.lowercase())
                }
            }
        }

        val exitCode = process.waitFor()
        if (exitCode != 0) return false

        return stdout.contains("usage: 86box [options]")
    }
}