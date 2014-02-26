package com.tpl.lib.gui

import net.minecraft.client.renderer.Tessellator
import net.bdew.lib.gui.{Color, TextureLocationScaled, Rect}
import net.minecraft.client.Minecraft

trait TextureRendering {

  def getZLevel: Float
  final val F = 1 / 256F

  def addVertexUVScaled(x: Float, y: Float, z: Float, u: Float, v: Float) {
    Tessellator.instance.addVertexWithUV(x, y, z, u * F, v * F)
  }

  def drawTextureScaled(r: Rect, l: TextureLocationScaled, color: Color = Color.white) {
    val t = l.r
    val z = getZLevel

    Minecraft.getMinecraft.renderEngine.bindTexture(l.resource)

    color.activate()
    Tessellator.instance.startDrawingQuads()
    addVertexUVScaled(r.x + r.w, r.y,       z, t.x + t.w,   t.y)
    addVertexUVScaled(r.x,       r.y,       z, t.x,         t.y)
    addVertexUVScaled(r.x,       r.y + r.h, z, t.x,         t.y + t.h)
    addVertexUVScaled(r.x + r.w, r.y + r.h, z, t.x + t.w,   t.y + t.h)
    Tessellator.instance.draw()
  }
}
