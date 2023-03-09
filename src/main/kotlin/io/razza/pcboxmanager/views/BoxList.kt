package io.razza.pcboxmanager.views

import io.razza.pcboxmanager.Application
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane

// FIXME: look. i tried making this suck as a least as possbile,
//        but swing isn't letting me have this W
class BoxList : JPanel(BorderLayout()) {
    init { refresh() }

    fun refresh() {
        if (components.isNotEmpty()) remove(0)
        add(buildComponent(), BorderLayout.CENTER)
        invalidate()
    }

    private fun buildComponent(): JScrollPane {
        return JScrollPane(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            for (box in Application.boxManager.knownBoxes) {
                add(BoxView(box))
            }
        }, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
    }
}
