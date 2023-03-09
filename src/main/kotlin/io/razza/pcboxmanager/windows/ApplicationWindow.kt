package io.razza.pcboxmanager.windows

import io.razza.pcboxmanager.EightySixBox
import io.razza.pcboxmanager.views.BoxList
import io.razza.pcboxmanager.views.ResourceIcon
import java.awt.BorderLayout
import java.awt.Desktop
import java.awt.Dimension
import java.net.URI
import javax.imageio.ImageIO
import javax.swing.*

class ApplicationWindow : JFrame() {
    val boxList = BoxList()

    init {
        iconImages = listOf(
            ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/logo/Logo.16.png")),
            ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/logo/Logo.32.png")),
            ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/logo/Logo.48.png")),
            ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/logo/Logo.64.png")),
            ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/logo/Logo.96.png")),
        )
        size = Dimension(800, 600)
        isLocationByPlatform = true
        defaultCloseOperation = EXIT_ON_CLOSE

        layout = BorderLayout()
        add(JToolBar().apply {
            add(JButton("New Box").apply {
                icon = ImageIcon(ResourceIcon.get("new-box"))

                addActionListener {
                    NewBoxDialog.open(this@ApplicationWindow)
                    boxList.refresh()
                }
            })
            add(JToolBar.Separator())
            add(JButton().apply {
                icon = ImageIcon(ResourceIcon.get("import-box"))
                toolTipText = "Import Box"

                addActionListener {
                    JOptionPane.showMessageDialog(
                        this@ApplicationWindow,
                        "Importing boxes is not yet implemented.",
                        "Not Implemented",
                        JOptionPane.INFORMATION_MESSAGE
                    )
                }
            })
            add(Box.createGlue())
            add(JButton("Settings"))
            add(JButton("About").apply {
//                Desktop.getDesktop().browse(URI("https://itisrazza.github.io/pcboxmanager"))
            })
        }, BorderLayout.NORTH)

        add(boxList, BorderLayout.CENTER)
    }
}