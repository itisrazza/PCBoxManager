package io.razza.pcboxmanager

import com.formdev.flatlaf.FlatDarkLaf
import io.razza.pcboxmanager.boxes.BoxManager
import io.razza.pcboxmanager.windows.ApplicationWindow
import io.razza.pcboxmanager.windows.FirstRunDialog

object Application {
    var window: ApplicationWindow? = null
        private set

    val boxManager = BoxManager()

    fun run() {
        window = ApplicationWindow().apply {
            isVisible = true
        }

        if (!Config.exists()) {
            FirstRunDialog(window).isVisible = true
        }
    }
}

fun main(args: Array<String>) {
    FlatDarkLaf.setup()
    Application.run()
}
