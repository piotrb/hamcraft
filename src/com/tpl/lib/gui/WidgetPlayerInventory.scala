package com.tpl.lib.gui

import net.bdew.lib.gui.{WidgetContainer, Rect, Color, TextureLocation}
import net.bdew.lib.gui.widgets.WidgetSubcontainer

class WidgetPlayerInventory(x: Int, y: Int, texture: TextureLocation, invColor: Color = Color.white, hotbarColor: Color = Color.white) extends WidgetSubcontainer(new Rect(x,y,(3*18)+4+18,9*18)) with WidgetContainer {
  (0 to 2).foreach(row => {
    (0 to 8).foreach(col => {
      add(new WidgetInventorySlot((col*18), (row*18), texture, invColor))
    })
  })

  val hotbarY = (3*18)+4

  (0 to 8).foreach(col => {
    add(new WidgetInventorySlot((col*18), hotbarY, texture, hotbarColor))
  })
}
