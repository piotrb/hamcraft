package com.tpl.lib.gui

import net.bdew.lib.power.{DataSlotPower, WidgetPowerGauge}
import net.bdew.lib.gui._

class WidgetPowerGaugeDynamic(rect: Rect, texture: Texture, dslot: DataSlotPower, bgTexture: Texture, fgOffset: Rect) extends WidgetPowerGauge(WidgetPowerGaugeDynamic.fgRect(rect, fgOffset), texture, dslot) {

  override def draw(mouse: Point) {
    val fill = dslot.stored / dslot.capacity
    val _fgRect = WidgetPowerGaugeDynamic.fgRect(rect, fgOffset)
    parent.drawTextureInterpolate(_fgRect, texture, 0, 1 - fill, 1, 1)
  }

  override def drawBackground(mouse: Point) {
    parent.drawTexture(rect, bgTexture)
  }

}

private object WidgetPowerGaugeDynamic {
  def fgRect(bgRect: Rect, fgOffset: Rect) = Rect(bgRect.x+fgOffset.x, bgRect.y+fgOffset.y, bgRect.w+fgOffset.w, bgRect.h+fgOffset.h)
}
