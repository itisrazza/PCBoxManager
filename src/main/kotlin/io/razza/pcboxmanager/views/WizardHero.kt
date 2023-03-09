package io.razza.pcboxmanager.views

import java.awt.Color
import java.awt.Dimension
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel

class WizardHero(image: String) : JLabel() {
    var image: String = image
        get() = field
        set(value) {
            val resourceStream = ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/wizards/$value.png")
            if (resourceStream == null) {
                System.err.println("WizardHero: A wizard sidebar image called '$value' does not exist.")
            }

            icon = ImageIcon(ImageIO.read(resourceStream))
            field = value
        }

    init {
        size = Dimension(150, 350)
        background = Color(0, 0, 128)
        this.image = image
    }
}