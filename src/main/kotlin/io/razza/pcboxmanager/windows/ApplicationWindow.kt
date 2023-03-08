package io.razza.pcboxmanager.windows

import io.razza.pcboxmanager.EightySixBox
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JSeparator
import javax.swing.JToolBar

class ApplicationWindow : JFrame() {
    init {
        size = Dimension(800, 600)
        isLocationByPlatform = true
        defaultCloseOperation = EXIT_ON_CLOSE

        layout = BorderLayout()
        add(JToolBar().apply {
            add(JButton("New Box").apply {
                addActionListener {
                    NewBoxDialog.open(this@ApplicationWindow)
                }
            })
            add(JButton("Open Box").apply {
                addActionListener {
                    EightySixBox().start()
                }
            })
            add(JSeparator(JSeparator.VERTICAL))
            add(JButton("Settings"))
        }, BorderLayout.NORTH)
    }
}