package com.tpl.lib.gui

import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.WidgetSubcontainer

class WidgetInventoryGrid(x: Int, y: Int, rows: Int, cols: Int, texture: TextureLocation, color: Color = Color.white) extends WidgetSubcontainer(new Rect(x,y,rows*18,cols*18)) with WidgetContainer {
  (0 to rows-1).foreach(row => {
    (0 to cols-1).foreach(col => {
      add(new WidgetInventorySlot((col*18), (row*18), texture, color))
    })
  })
}
