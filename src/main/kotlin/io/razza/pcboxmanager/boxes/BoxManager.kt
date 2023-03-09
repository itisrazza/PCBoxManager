package io.razza.pcboxmanager.boxes

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.razza.pcboxmanager.Config
import io.razza.pcboxmanager.EightySixBox
import java.util.*
import kotlin.io.path.exists

class BoxManager {
    private val knownBoxesField = ArrayList<Box>()
    private val runningBoxesField = HashSet<Box>()
    private val gson = Gson()

    init {
        load()
    }

    val knownBoxes get() = Collections.unmodifiableList(knownBoxesField)
    val runningBoxes get() = Collections.unmodifiableSet(runningBoxesField)

    fun start(box: Box) {
        box.lock()?.throwIfSet()

        val process = EightySixBox()
            .withVmName(box.name)
            .withVmPath(box.path)
            .withConfig(box.configPath.toString())
            .start()
        val lock = BoxLock(box, process.pid(), BoxLockReason.RUNNING).save(box.lockPath.toFile())

        process.onExit().whenComplete { _, _ -> lock.close() }
        runningBoxesField.add(box)
    }

    fun configure(box: Box) {
        box.lock()?.throwIfSet()

        val process = EightySixBox()
            .withVmName(box.name)
            .withVmPath(box.path)
            .withConfig(box.configPath.toString())
            .withSettingsOnly()
            .start()
        val lock = BoxLock(box, process.pid(), BoxLockReason.CONFIGURE).save(box.lockPath.toFile())

        process.onExit().whenComplete { _, _ -> lock.close() }
    }

    /**
     * Add a box to the library.
     */
    fun add(box: Box) {
        knownBoxesField.add(box)
        save()
    }

    /**
     * Remove a box from the library.
     */
    fun remove(box: Box) {
        TODO()
    }

    private fun save() {
        Config.libraryPath.toFile().writer(Charsets.UTF_8).use { writer ->
            gson.toJson(knownBoxesField, writer)
        }
    }

    private fun load() {
        if (!Config.libraryPath.exists()) return

        Config.libraryPath.toFile().reader(Charsets.UTF_8).use { reader ->
            val boxes = gson.fromJson<List<Box>>(reader, object : TypeToken<List<Box>>() {}.type)

            knownBoxesField.clear()
            knownBoxesField.addAll(boxes)
        }
    }
}