package com.tpl.hamcraft.gui

import net.bdew.lib.gui.{TextureLocationScaled, Rect, TextureLocation}

class GuiTextureLocation(guiTexture: TextureLocation) {
  private lazy val resource = guiTexture.resource
  private def offsetRect(x: Int, y: Int, w: Int, h: Int) = new Rect(guiTexture.p.x + x, guiTexture.p.y + y, w, h)

  lazy val topLeft     = new TextureLocationScaled(resource, offsetRect(0,0,4,4))
  lazy val topSide     = new TextureLocationScaled(resource, offsetRect(4,0,4,4))
  lazy val topRight    = new TextureLocationScaled(resource, offsetRect(8,0,4,4))

  lazy val leftSide    = new TextureLocationScaled(resource, offsetRect(0,4,4,4))
  lazy val center      = new TextureLocationScaled(resource, offsetRect(4,4,4,4))
  lazy val rightSide   = new TextureLocationScaled(resource, offsetRect(8,4,4,4))

  lazy val bottomLeft  = new TextureLocationScaled(resource, offsetRect(0,8,4,4))
  lazy val bottomSide  = new TextureLocationScaled(resource, offsetRect(4,8,4,4))
  lazy val bottomRight = new TextureLocationScaled(resource, offsetRect(8,8,4,4))
}
