package io.razza.pcboxmanager.windows

import io.razza.pcboxmanager.Application
import io.razza.pcboxmanager.Config
import io.razza.pcboxmanager.views.SidebarImage
import io.razza.pcboxmanager.views.WizardHero
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ForkJoinTask
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.EmptyBorder
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

class NewBoxDialog(parentWindow: Window?) : JDialog(parentWindow, ModalityType.APPLICATION_MODAL) {
    val boxNameField: JTextField
    val boxPathLabel: JLabel

    init {
        title = "Create a new box"
        isResizable = false
        isModal = true
        size = Dimension(480, 350)
        setLocationRelativeTo(parentWindow)
        defaultCloseOperation = DISPOSE_ON_CLOSE

        layout = BorderLayout()

        add(WizardHero("new-box"), BorderLayout.WEST)
        add(JPanel(BorderLayout()).apply {
            add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)

                add(JPanel(FlowLayout(FlowLayout.LEADING)).apply {
                    border = EmptyBorder(4, 4, 0, 4)

                    add(JLabel("Create a New Box").apply {
                        font = font.deriveFont(Font.BOLD, 18f)
                        horizontalAlignment = SwingConstants.LEFT
                    })
                })

                add(JTextArea("Enter the name for the new box, its location and which template to use.").apply {
                    isEditable = false
                    lineWrap = true
                    wrapStyleWord = true
                    border = EmptyBorder(8, 8, 8, 8)
                })

                add(JPanel(GridBagLayout()).apply {
                    add(JLabel("Name:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 1
                        anchor = GridBagConstraints.EAST
                        insets = Insets(4, 8, 4, 8)
                    })
                    add(JTextField().apply {
                        boxNameField = this
                    }, GridBagConstraints().apply {
                        gridx = 1
                        gridy = 1
                        gridwidth = 2
                        weightx = 1.0
                        fill = GridBagConstraints.HORIZONTAL
                        insets = Insets(0, 0, 0, 8)
                    })

                    add(JLabel("Location:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 2
                        anchor = GridBagConstraints.EAST
                        insets = Insets(4, 8, 4, 8)
                    })
                    add(JLabel("<default>").apply {
                        boxPathLabel = this
                    }, GridBagConstraints().apply {
                        gridx = 1
                        gridy = 2
                        weightx = 1.0
                        fill = GridBagConstraints.HORIZONTAL
                    })
                    add(JButton("Browse...").apply {
                        addActionListener { onBoxPathBrowseClicked() }
                    }, GridBagConstraints().apply {
                        gridx = 2
                        gridy = 2
                        insets = Insets(4, 8, 4, 8)
                    })
                })

                add(
                    Box.Filler(
                        Dimension(0, 0),
                        Dimension(Int.MAX_VALUE, Int.MAX_VALUE),
                        Dimension(Int.MAX_VALUE, Int.MAX_VALUE)
                    )
                )
            }, BorderLayout.CENTER)

            add(JPanel(FlowLayout(FlowLayout.TRAILING)).apply {
                border = EmptyBorder(8, 8, 8, 8)

                add(JButton("Create").apply {
                    addActionListener { onCreateClicked() }
                })
            }, BorderLayout.SOUTH)
        })
    }

    private fun onBoxPathBrowseClicked() {
        val fileChooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        }

        val result = fileChooser.showOpenDialog(this)
        if (result != JFileChooser.APPROVE_OPTION) return

        boxPathLabel.text = fileChooser.selectedFile.absolutePath.toString()
    }

    private fun onCreateClicked() {
        if (boxNameField.text.isBlank()) {
            JOptionPane.showMessageDialog(
                this,
                "You need to give your box a name.",
                this.title,
                JOptionPane.ERROR_MESSAGE,
                null
            )
            return
        }

        if (boxNameField.text.contains('/')) {
            JOptionPane.showMessageDialog(
                this,
                "The box name contains invalid characters.",
                this.title,
                JOptionPane.ERROR_MESSAGE,
                null
            )
            return
        }

        var path = if (boxPathLabel.text == "<default>")
            Config.defaultBoxPath.resolve(boxNameField.text)
        else
            Path.of(boxPathLabel.text)

        if (path.exists()) {
            JOptionPane.showMessageDialog(
                this,
                "The box location already exists.",
                this.title,
                JOptionPane.ERROR_MESSAGE,
                null
            )
            return
        }

        try {
            path.createDirectory()
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(
                this,
                "The box path is invalid.",
                this.title,
                JOptionPane.ERROR_MESSAGE,
                null
            )
            return
        }

        val box = io.razza.pcboxmanager.boxes.Box(boxNameField.text, path.absolutePathString())
        Application.boxManager.add(box)

        dispose()
    }

    companion object {
        fun open(parentWindow: Window? = null): Any {
            val dialog = NewBoxDialog(parentWindow)
            dialog.isVisible = true
            return Any()
        }
    }
}