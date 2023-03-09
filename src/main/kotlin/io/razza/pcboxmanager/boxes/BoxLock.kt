package io.razza.pcboxmanager.boxes

import com.google.gson.Gson
import java.io.File
import kotlin.io.path.deleteIfExists

/**
 * Data written to and read from a Box lock file.
 */
data class BoxLock(
    /**
     * The box this lock belongs to.
     */
    @Transient val box: Box,

    /**
     * The process locking the box.
     */
    val pid: Long,

    /**
     * The reason for the lock.
     */
    val reason: BoxLockReason
) : AutoCloseable {
    /**
     * Save the lock into a lock file.
     */
    fun save(path: File): BoxLock =
        path.writer(Charsets.UTF_8).use { writer ->
            Gson().toJson(this, writer)
            return@save this
        }

    /**
     * Throw an exception if the lock is set.
     */
    fun throwIfSet() {
        if (ProcessHandle.of(pid).isPresent) {
            throw BoxLockedException(this)
        }
        close() // delete the file it the process doesn't exist
    }

    /**
     * Release the lock on the box.
     */
    override fun close() {
        box.lockPath.deleteIfExists()
    }

    companion object {
        /**
         * Load a lock from a lock file.
         */
        fun load(path: File): BoxLock =
            path.reader(Charsets.UTF_8).use { reader ->
                Gson().fromJson(reader, BoxLock::class.java)
            }
    }
}

enum class BoxLockReason {
    RUNNING,
    CONFIGURE,
}
