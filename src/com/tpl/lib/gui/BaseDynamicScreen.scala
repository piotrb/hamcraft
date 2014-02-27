package com.tpl.lib.gui

import net.bdew.lib.gui._
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import com.tpl.lib.gui.config.GuiConfig
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.{RenderHelper, Tessellator}
import org.lwjgl.opengl.{GL12, GL11}

trait DrawTarget2 {
  def getZLevel: Float
  def getFontRenderer: FontRenderer
  def drawTexture(r: Rect, t: Texture, color: Color = Color.white)
  def drawText(text: String, p: Point, color: Color, shadow: Boolean)
  def drawTextMultiline(text: String, r: Rect, color: Color)

  def drawTextureInterpolate(r: Rect, t: Texture, x1: Float, y1: Float, x2: Float, y2: Float, color: Color = Color.white) =
    drawTexture(r.interpolate(x1, y1, x2, y2), Texture.interpolate(t, x1, y1, x2, y2))
}

trait SimpleDrawTarget2 extends DrawTarget2 {
  private final val T = Tessellator.instance

  def drawTexture(r: Rect, t: Texture, color: Color = Color.white) {
    val z = getZLevel
    t.bind()
    color.activate()
    T.startDrawingQuads()
    T.addVertexWithUV(r.x1, r.y2, z, t.u1, t.v2)
    T.addVertexWithUV(r.x2, r.y2, z, t.u2, t.v2)
    T.addVertexWithUV(r.x2, r.y1, z, t.u2, t.v1)
    T.addVertexWithUV(r.x1, r.y1, z, t.u1, t.v1)
    T.draw()
  }

  def drawText(text: String, p: Point, color: Color, shadow: Boolean) =
    getFontRenderer.drawString(text, p.x.round, p.y.round, color.asARGB, shadow)

  def drawTextMultiline(text: String, r: Rect, color: Color) =
    getFontRenderer.drawSplitString(text, r.x.round, r.y.round, r.w.round, color.asARGB)
}

class BaseDynamicScreen(cont: Container, var config: GuiConfig) extends BaseScreen(cont, config.window.rect.w.toInt, config.window.rect.h.toInt) with SimpleDrawTarget2 {

  override protected def drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {

    config.window.mode match {
      case "dynamic" => drawDynamicBackground
//      case "static" => drawStaticBackground
      case _ =>
        throw new Exception(String.format("don't know how to deal with mode: %s", config.window.mode))
    }

    GL11.glPushMatrix
    GL11.glTranslatef(this.guiLeft.toFloat, this.guiTop.toFloat, 0.0F)
    backgroundWidgets.draw(Point(x, y) - rect.origin)
    GL11.glPopMatrix

  }

  val backgroundWidgets = new WidgetContainerWindow(this, config.window.rect.w.toInt, config.window.rect.h.toInt)

  override def initGui() {
    super.initGui()
    backgroundWidgets.clear()
  }

//  private def drawStaticBackground {
//
//  }

  private def drawDynamicBackground {
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2

    val gui: GuiTextureLocation = config.window.dynamicGuiTexture
    val bgTileSize: Int = gui.size

    drawTexture(new Rect(x+0, y+0, bgTileSize, bgTileSize), gui.topLeft)
    drawTexture(new Rect(x+bgTileSize, y+0, xSize - bgTileSize - bgTileSize, bgTileSize), gui.topSide)
    drawTexture(new Rect(x+xSize - bgTileSize, y+0, bgTileSize, bgTileSize), gui.topRight)

    drawTexture(new Rect(x+0, y+bgTileSize, bgTileSize, ySize - bgTileSize - bgTileSize), gui.leftSide)
    drawTexture(new Rect(x+bgTileSize, y+bgTileSize, xSize - bgTileSize - bgTileSize, ySize - bgTileSize - bgTileSize), gui.center)
    drawTexture(new Rect(x+xSize - bgTileSize, y+bgTileSize, bgTileSize, ySize - bgTileSize - bgTileSize), gui.rightSide)

    drawTexture(new Rect(x+0, y+ySize - bgTileSize, bgTileSize, bgTileSize), gui.bottomLeft)
    drawTexture(new Rect(x+bgTileSize, y+ySize - bgTileSize, xSize - bgTileSize - bgTileSize, bgTileSize), gui.bottomSide)
    drawTexture(new Rect(x+xSize - bgTileSize, y+ySize - bgTileSize,bgTileSize,bgTileSize), gui.bottomRight)
  }

  override val texture: ResourceLocation = null
}
