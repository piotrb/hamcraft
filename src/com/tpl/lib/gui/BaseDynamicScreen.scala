package com.tpl.lib.gui

import com.tpl.lib.gui.config.GuiConfig
import net.bdew.lib.gui._
import net.minecraft.inventory.Container

class BaseDynamicScreen(cont: Container, var config: GuiConfig) extends BaseScreen(cont, config.window.rect.w.toInt, config.window.rect.h.toInt) with SimpleDrawTarget {

  val background: Texture = null

  override protected def drawGuiContainerBackgroundLayer(f: Float, x: Int, y: Int) {
    config.window.mode match {
      case "dynamic" => drawDynamicBackground
      //      case "static" => drawStaticBackground
      case _ =>
        throw new Exception(String.format("don't know how to deal with mode: %s", config.window.mode))
    }

    super.drawGuiContainerBackgroundLayer(f, x, y)
  }

  //  private def drawStaticBackground {
  //
  //  }

  private def drawDynamicBackground {
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2

    val gui: GuiTextureLocation = config.window.dynamicGuiTexture
    val bgTileSize: Int = gui.size

    drawTexture(new Rect(x + 0, y + 0, bgTileSize, bgTileSize), gui.topLeft)
    drawTexture(new Rect(x + bgTileSize, y + 0, xSize - bgTileSize - bgTileSize, bgTileSize), gui.topSide)
    drawTexture(new Rect(x + xSize - bgTileSize, y + 0, bgTileSize, bgTileSize), gui.topRight)

    drawTexture(new Rect(x + 0, y + bgTileSize, bgTileSize, ySize - bgTileSize - bgTileSize), gui.leftSide)
    drawTexture(new Rect(x + bgTileSize, y + bgTileSize, xSize - bgTileSize - bgTileSize, ySize - bgTileSize - bgTileSize), gui.center)
    drawTexture(new Rect(x + xSize - bgTileSize, y + bgTileSize, bgTileSize, ySize - bgTileSize - bgTileSize), gui.rightSide)

    drawTexture(new Rect(x + 0, y + ySize - bgTileSize, bgTileSize, bgTileSize), gui.bottomLeft)
    drawTexture(new Rect(x + bgTileSize, y + ySize - bgTileSize, xSize - bgTileSize - bgTileSize, bgTileSize), gui.bottomSide)
    drawTexture(new Rect(x + xSize - bgTileSize, y + ySize - bgTileSize, bgTileSize, bgTileSize), gui.bottomRight)
  }

}
