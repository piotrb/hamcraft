package com.tpl.lib.gui

import net.bdew.lib.gui._

class GuiTextureLocation(resource: ScaledResourceLocation, origin: Point, val size: Int = 4) {
  private def offsetRect(x: Int, y: Int, w: Int, h: Int) = new Rect(origin.x + x, origin.y + y, w, h)

  val topLeft     = Texture.apply(resource, offsetRect(0,0,size,size))
  val topSide     = Texture.apply(resource, offsetRect(size,0,size,size))
  val topRight    = Texture.apply(resource, offsetRect(size+size,0,size,size))

  val leftSide    = Texture.apply(resource, offsetRect(0,size,size,size))
  val center      = Texture.apply(resource, offsetRect(size,size,size,size))
  val rightSide   = Texture.apply(resource, offsetRect(size+size,size,size,size))

  val bottomLeft  = Texture.apply(resource, offsetRect(0,size+size,size,size))
  val bottomSide  = Texture.apply(resource, offsetRect(size,size+size,size,size))
  val bottomRight = Texture.apply(resource, offsetRect(size+size,size+size,size,size))
}
