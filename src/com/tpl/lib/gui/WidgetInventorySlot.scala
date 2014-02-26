package com.tpl.lib.gui

import net.bdew.lib.gui.{Color, Point, Rect, TextureLocation}
import net.bdew.lib.gui.widgets.Widget
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL11

class WidgetInventorySlot(x: Int, y: Int, texture: TextureLocation, color: Color = Color.white) extends Widget {
  val rect: Rect = new Rect(x, y, 18, 18)

  override def draw(mouse: Point) {
    Minecraft.getMinecraft.renderEngine.bindTexture(texture.resource)
    GL11.glEnable(GL11.GL_BLEND)
    parent.drawTexture(Rect(rect.x, rect.y, rect.w, rect.h), Point(texture.x, texture.y), color)
    GL11.glDisable(GL11.GL_BLEND)
  }
}
