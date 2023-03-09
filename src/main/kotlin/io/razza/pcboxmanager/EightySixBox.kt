package io.razza.pcboxmanager

import java.io.*

/**
 * Class to build 86Box command-line arguments.
 */
class EightySixBox(
    val program: String = "86Box",
    val arguments: List<String> = listOf(),
    val workingDirectory: File? = null
) {
    /**
     * Sets the configuration file 86box uses.
     */
    fun withConfig(path: String) = appendArguments("--config", path)

    /**
     * Starts 86box in full screen mode.
     */
    fun withFullscreen() = appendArguments("--fullscreen")

    /**
     * Mounts a floppy disk image on a drive letter.
     */
    fun withImage(driveLetter: Char, path: String) = EightySixBox(
        program,
        arguments + "--image" + "$driveLetter:$path",
        workingDirectory
    )

    /**
     * Sets the log file 86box writes into.
     */
    fun withLogFile(path: String) = appendArguments("--logfile", path)

    fun withNoConfirm() = appendArguments("--noconfirm")

    fun withDumpConfig() = appendArguments("--dumpcfg")

    fun withVmPath(path: String) = appendArguments("--vmpath", path)
        .setWorkingDirectory(File(path))

    fun withRomPath(path: String) = appendArguments("--rompath", path)

    fun withSettingsOnly() = appendArguments("--settings")

    fun withVmName(name: String) = appendArguments("--vmname", name)

    /**
     * Starts 86box.
     */
    fun start() = ProcessBuilder(program, *arguments.toTypedArray())
        .directory(workingDirectory)
        .start()!!

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

    private fun appendArguments(vararg arguments: String) = EightySixBox(
        program,
        this.arguments.plus(arguments),
        workingDirectory
    )

    private fun setWorkingDirectory(directory: File) = EightySixBox(program, arguments, directory)
}