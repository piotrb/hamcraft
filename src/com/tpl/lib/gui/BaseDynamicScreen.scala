package com.tpl.lib.gui

import net.bdew.lib.gui._
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import com.tpl.lib.gui.config.GuiConfig

class BaseDynamicScreen(cont: Container, var config: GuiConfig) extends BaseScreen(cont, config.window.size.w, config.window.size.h) with TextureRendering {

  override protected def drawGuiContainerBackgroundLayer(f: Float, i: Int, j: Int) {
    val x = (width - xSize) / 2
    val y = (height - ySize) / 2

    val gui = new GuiTextureLocation(config.window.texture)

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

  override val texture: ResourceLocation = null
}
