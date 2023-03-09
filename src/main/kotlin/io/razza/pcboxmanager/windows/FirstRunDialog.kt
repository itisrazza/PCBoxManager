package io.razza.pcboxmanager.windows

import io.razza.pcboxmanager.Config
import io.razza.pcboxmanager.EightySixBox
import io.razza.pcboxmanager.views.WizardHero
import org.apache.commons.lang3.SystemUtils
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.border.EmptyBorder

class FirstRunDialog(val parentWindow: Window? = null) : JDialog(parentWindow) {
    private var eightySixBoxPathLabel: JLabel
    private var defaultBoxPathLabel: JLabel

    init {
        title = "Welcome to PCBoxManager"
        isResizable = false
        isModal = true
        size = Dimension(480, 350)
        setLocationRelativeTo(parentWindow)
        defaultCloseOperation = DISPOSE_ON_CLOSE

        layout = BorderLayout()

        add(WizardHero("initial-setup"), BorderLayout.WEST)
        add(JPanel(BorderLayout()).apply {
            add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)

                add(JPanel(FlowLayout(FlowLayout.LEADING)).apply {
                    border = EmptyBorder(4, 4, 0, 4)

                    add(JLabel("Welcome").apply {
                        font = font.deriveFont(Font.BOLD, 18f)
                        horizontalAlignment = SwingConstants.LEFT
                    })
                })

                add(JTextArea("Please ensure 86Box is available in the command path, or manually select the paths below.").apply {
                    isEditable = false
                    lineWrap = true
                    wrapStyleWord = true
                    border = EmptyBorder(8, 8, 8, 8)
                })

                add(JPanel(GridBagLayout()).apply {
                    add(JLabel("86Box path:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 1
                        anchor = GridBagConstraints.EAST
                        insets = Insets(4, 8, 4, 8)
                    })
                    add(JLabel("<default>").apply {
                        eightySixBoxPathLabel = this
                    }, GridBagConstraints().apply {
                        gridx = 1
                        gridy = 1
                        weightx = 1.0
                        fill = GridBagConstraints.HORIZONTAL
                    })
                    add(JButton("Browse...").apply {
                        addActionListener { onEightySixBrowseClicked() }
                    }, GridBagConstraints().apply {
                        gridx = 2
                        gridy = 1
                        insets = Insets(4, 8, 4, 8)
                    })

                    add(JLabel("Library path:"), GridBagConstraints().apply {
                        gridx = 0
                        gridy = 2
                        anchor = GridBagConstraints.EAST
                        insets = Insets(4, 8, 4, 8)
                    })
                    add(JLabel("<default>").apply {
                        defaultBoxPathLabel = this
                    }, GridBagConstraints().apply {
                        gridx = 1
                        gridy = 2
                        weightx = 1.0
                        fill = GridBagConstraints.HORIZONTAL
                    })
                    add(JButton("Browse...").apply {
                        addActionListener { onDefaultBoxPathBrowseClicked() }
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

                add(JButton("Finish").apply {
                    addActionListener { finishAndClose() }
                })
            }, BorderLayout.SOUTH)
        })

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                finishAndClose()
            }
        })
    }

    private fun onEightySixBrowseClicked() {
        var validResult: String? = null
        val fileChooser = JFileChooser().apply {
            currentDirectory = SystemUtils.getUserHome()
        }

        while (validResult == null) {
            val result = fileChooser.showOpenDialog(this@FirstRunDialog)
            if (result != JFileChooser.APPROVE_OPTION) return

            val path = fileChooser.selectedFile.path
            if (!EightySixBox(path).validate()) {
                JOptionPane.showMessageDialog(
                    this@FirstRunDialog,
                    "The selected program does not seem to be 86Box. Please choose another one.",
                    "Selected program invalid",
                    JOptionPane.ERROR_MESSAGE
                )
                continue
            }
            validResult = fileChooser.selectedFile.path
        }

        Config.eightySixBoxPath = validResult
        eightySixBoxPathLabel.text = validResult
    }

    private fun onDefaultBoxPathBrowseClicked() {
        val fileChooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        }

        val result = fileChooser.showOpenDialog(this@FirstRunDialog)
        if (result != JFileChooser.APPROVE_OPTION) return

        Config.defaultBoxPath = fileChooser.selectedFile.toPath().toAbsolutePath()
        defaultBoxPathLabel.text = Config.defaultBoxPath.toString()
    }

    private fun finishAndClose() {
        Config.save()
        dispose()
    }
}

class FirstRunPage(image: ImageIcon, child: Component) : JPanel(BorderLayout()) {
    init {
        add(JLabel().apply {
            size = Dimension(320, 200)
            icon = image
        }, BorderLayout.NORTH)
        add(child, BorderLayout.CENTER)
    }
}
