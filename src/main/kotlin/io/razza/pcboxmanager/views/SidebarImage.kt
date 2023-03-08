package io.razza.pcboxmanager.views

import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.JPanel

class SidebarImage : JPanel() {
    init {
        add(JLabel("Imagine very 90s wizard\nsidebar image here"))
        preferredSize = Dimension(200, 300)
    }
}