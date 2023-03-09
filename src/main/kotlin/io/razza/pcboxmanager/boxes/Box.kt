package io.razza.pcboxmanager.boxes

import io.razza.pcboxmanager.EightySixBox
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

/**
 * Represents a box (our terminology for a Virtual Machine) and provides helpers for configuration paths.
 */
class Box(val name: String, val path: String) {
    val configPath: Path get() = Paths.get(path).resolve("config.json")
    val lockPath: Path get() = Paths.get(path).resolve("lock.json")

    fun lock(): BoxLock? =
        if (!lockPath.exists()) null
        else BoxLock.load(lockPath.toFile())

    override fun toString() = "Box(name = '$name', path = '$path')"
}