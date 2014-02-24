package com.tpl.hamcraft.gui

import net.bdew.lib.gui._
import net.minecraft.inventory.Container
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator

class BaseDynamicScreen(cont: Container, w: Int, h: Int, guiTexture: TextureLocation) extends BaseScreen(cont, w, h) {
  lazy val texture = guiTexture.resource
  lazy val gui = new GuiTextureLocation(guiTexture)

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

  override protected def drawGuiContainerBackgroundLayer(f: Float, i: Int, j: Int) {
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2

    drawTextureScaled(new Rect(x+0, y+0, 4, 4), gui.topLeft)
    drawTextureScaled(new Rect(x+4, y+0, xSize - 4 - 4, 4), gui.topSide)
    drawTextureScaled(new Rect(x+xSize - 4, y+0, 4, 4), gui.topRight)

    drawTextureScaled(new Rect(x+0, y+4, 4, ySize - 4 - 4), gui.leftSide)
    drawTextureScaled(new Rect(x+4, y+4, xSize - 4 - 4, ySize - 4 - 4), gui.center)
    drawTextureScaled(new Rect(x+xSize - 4, y+4, 4, ySize - 4 - 4), gui.rightSide)

    drawTextureScaled(new Rect(x+0, y+ySize - 4, 4, 4), gui.bottomLeft)
    drawTextureScaled(new Rect(x+4, y+ySize - 4, xSize - 4 - 4, 4), gui.bottomSide)
    drawTextureScaled(new Rect(x+xSize - 4, y+ySize - 4,4,4), gui.bottomRight)

  }
}
