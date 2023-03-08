package io.razza.pcboxmanager

import com.google.gson.Gson
import org.apache.commons.lang3.SystemUtils
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

object Config {
    private val gson = Gson()
    val appDataFolder get() = appDataFolder()

    val configPath get() = appDataFolder.resolve("config.json")
    val libraryPath get() = appDataFolder.resolve("library.json")
    private var configData = ConfigData()

    var eightySixBoxPath: String
        get() = configData.eightySixBoxPath
        set(value) {
            configData.eightySixBoxPath = value
        }

    var defaultBoxPath: Path
        get() = Paths.get(configData.defaultBoxPath)
        set(value) {
            configData.defaultBoxPath = value.absolutePathString()
        }

    /**
     * Checks if the configuration and virtual machine library files exist.
     */
    fun exists() = configPath.exists()

    /**
     * Load the configuration data.
     */
    fun load() {
        configPath.toFile().reader(Charsets.UTF_8).use { reader ->
            configData = gson.fromJson(reader, ConfigData::class.java)
        }
    }

    fun save() {
        if (!appDataFolder.exists()) appDataFolder.createDirectory()
        if (!defaultBoxPath.exists()) defaultBoxPath.createDirectory()

        configPath.toFile().writer(Charsets.UTF_8).use { writer ->
            gson.toJson(configData, writer)
        }
    }

    private fun appDataFolder(): Path =
        if (SystemUtils.IS_OS_WINDOWS) Paths.get(
            SystemUtils.getEnvironmentVariable("LOCALAPPDATA", "C:\\"),
            "PCBoxManager"
        )
        else SystemUtils.getUserHome().toPath().resolve(".config").resolve("PCBoxManager").toAbsolutePath()
}

private data class ConfigData(
    var eightySixBoxPath: String = "86Box",
    var defaultBoxPath: String = Config.appDataFolder.resolve("boxes").toString(),
)
