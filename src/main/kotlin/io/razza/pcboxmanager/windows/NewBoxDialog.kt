package io.razza.pcboxmanager.windows

import io.razza.pcboxmanager.views.SidebarImage
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Window
import javax.swing.*

class NewBoxDialog(parentWindow: Window?) : JDialog(parentWindow) {
    init {
        title = "Create a New Box"
        size = Dimension(640, 480)
        setLocationRelativeTo(parentWindow)

        layout = BorderLayout()
        add(SidebarImage(), BorderLayout.WEST)
        add(JPanel(BorderLayout()).apply {
            add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)

                add(JLabel("Create a New Box").apply {
                    font = font.deriveFont(Font.BOLD, 18f)
                    alignmentX = 0f
                })
                add(JLabel("Please choose a descriptive name and destination folder for the new box.").apply {
                    alignmentX = 0f
                })

                add(JPanel(GridBagLayout()).apply {

                    add(JLabel("Name:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 0
                        gridwidth = 1
                    })
                    add(JTextField(80), GridBagConstraints().apply {
                        gridx = 1
                        gridy = 0
                        fill = GridBagConstraints.HORIZONTAL
                        gridwidth = 2
                    })

                    add(JLabel("Folder:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 1
                        gridwidth = 1
                    })
                    add(JTextField(80), GridBagConstraints().apply {
                        gridx = 1
                        gridy = 1
                        fill = GridBagConstraints.HORIZONTAL
                        gridwidth = 1
                    })

                    add(JLabel("Template:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 2
                        gridwidth = 1
                    })
                    add(JTextField(80), GridBagConstraints().apply {
                        gridx = 1
                        gridy = 2
                        fill = GridBagConstraints.HORIZONTAL
                        gridwidth = 2
                    })
                })

                add(Box.createVerticalGlue())
            }, BorderLayout.CENTER)
            add(JPanel(FlowLayout(FlowLayout.RIGHT)).apply {
                add(JButton("Create"))
                add(JButton("Cancel"))
            }, BorderLayout.SOUTH)
        }, BorderLayout.CENTER)
    }

    companion object {
        fun open(parentWindow: Window? = null): Any {
            val dialog = NewBoxDialog(parentWindow)
            dialog.isVisible = true
            return Any()
        }
    }
}