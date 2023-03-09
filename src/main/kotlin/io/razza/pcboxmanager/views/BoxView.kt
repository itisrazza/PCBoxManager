package io.razza.pcboxmanager.views

import io.razza.pcboxmanager.Application
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridBagLayout
import java.util.concurrent.Flow
import javax.swing.*
import javax.swing.border.EmptyBorder

class BoxView(box: io.razza.pcboxmanager.boxes.Box) : JPanel(FlowLayout()) {
    init {
        preferredSize = minimumSize

        layout = BoxLayout(this, BoxLayout.X_AXIS)

        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            border = EmptyBorder(8, 8, 8, 8)

            add(JLabel(box.name).apply {
                font = font.deriveFont(Font.BOLD)
                horizontalAlignment = SwingConstants.LEFT
            })
            add(JLabel(if (Application.boxManager.runningBoxes.contains(box)) "Running" else "Stopped").apply {
                horizontalAlignment = SwingConstants.LEFT
            })

            add(Box.createVerticalGlue())
        })
//        javax.swing.Box.createHorizontalGlue()
        add(JPanel(FlowLayout(FlowLayout.TRAILING)).apply {
            border = EmptyBorder(8, 8, 8, 8)

            add(JButton("Settings").apply {
                addActionListener { Application.boxManager.configure(box) }
            })
            add(JButton("Start").apply {
                addActionListener { Application.boxManager.start(box) }
            })
        })
    }
}