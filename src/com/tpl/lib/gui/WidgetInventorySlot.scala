package com.tpl.lib.gui

import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.Widget
import org.lwjgl.opengl.GL11

class WidgetInventorySlot(x: Int, y: Int, texture: Texture, color: Color = Color.white) extends Widget {
  val rect: Rect = new Rect(x, y, 18, 18)

  override def draw(mouse: Point) {
    GL11.glEnable(GL11.GL_BLEND)
    parent.drawTexture(Rect(rect.x, rect.y, rect.w, rect.h), texture, color)
    GL11.glDisable(GL11.GL_BLEND)
  }
}
