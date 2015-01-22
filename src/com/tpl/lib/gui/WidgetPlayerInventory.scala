package com.tpl.lib.gui

import net.bdew.lib.gui.{Texture, WidgetContainer, Rect, Color}
import net.bdew.lib.gui.widgets.WidgetSubcontainer

class WidgetPlayerInventory(x: Int, y: Int, texture: Texture, invColor: Color = Color.white, hotbarColor: Color = Color.white, hotbarSpace: Int = 4, cellSize: Int = 18)
  extends WidgetSubcontainer(new Rect(x,y,(3*cellSize)+hotbarSpace+18,9*cellSize)) with WidgetContainer {
  (0 to 2).foreach(row => {
    (0 to 8).foreach(col => {
      add(new WidgetInventorySlot((col*cellSize), (row*cellSize), texture, invColor))
    })
  })

  val hotbarY = (3*cellSize)+hotbarSpace

  (0 to 8).foreach(col => {
    add(new WidgetInventorySlot((col*cellSize), hotbarY, texture, hotbarColor))
  })
}
