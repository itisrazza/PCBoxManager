package io.razza.pcboxmanager.views

import javax.imageio.ImageIO

object ResourceIcon {
    fun get(name: String) = ImageIO.read(ClassLoader.getSystemResourceAsStream("io/razza/pcboxmanager/icons/$name.png"))
}