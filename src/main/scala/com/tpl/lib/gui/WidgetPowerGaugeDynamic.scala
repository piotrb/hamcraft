package com.tpl.lib.gui

import net.bdew.lib.power.DataSlotPower
import net.bdew.lib.gui._

class WidgetPowerGaugeDynamic(val rect: Rect, texture: Texture, dslot: DataSlotPower, bgTexture: Texture, fgOffset: Rect) extends DynamicWidget(bgTexture, fgOffset) {
  val widget = new WidgetPowerCustom(fgRect, texture, dslot)
}
