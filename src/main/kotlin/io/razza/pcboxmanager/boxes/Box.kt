package io.razza.pcboxmanager.boxes

import io.razza.pcboxmanager.EightySixBox
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

/**
 * Represents a box (our terminology for a Virtual Machine) and provides helpers for configuration paths.
 */
class Box(val name: String, val path: Path) {
    @Transient val configPath = path.resolve("config.json")
    @Transient val lockPath = path.resolve("lock.json")

    fun lock(): BoxLock? =
        if (!lockPath.exists()) null
        else BoxLock.load(lockPath.toFile())
}