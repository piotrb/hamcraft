package com.tpl.hamcraft.gui

import net.bdew.lib.gui.{Texture, Rect, Point}
import net.bdew.lib.power.{WidgetPowerGauge, DataSlotPower}
import com.tpl.hamcraft.config.Config
import scala.collection.mutable

class WidgetPowerCustom(rect: Rect, texture: Texture, dslot: DataSlotPower) extends WidgetPowerGauge(rect, texture, dslot) {
  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) =
    tip += formater.format(dslot.stored * Config.powerShowMultiplier) + "/" + formater.format(dslot.capacity * Config.powerShowMultiplier) + " " + Config.powerShowUnits
}
