package com.tpl.hamcraft.gui

import net.bdew.lib.gui.{Point, TextureLocation, Rect}
import net.bdew.lib.data.DataSlotFloat
import net.bdew.lib.gui.widgets.WidgetProgressBar
import net.bdew.lib.Misc
import scala.collection.mutable

import com.tpl.hamcraft.nei.NEIRecipeProxy

class WidgetProgressBarNEI(rect: Rect, texture: TextureLocation, dslot: DataSlotFloat, recipeid: String) extends WidgetProgressBar(rect, texture, dslot) {

  override def mouseClicked(p: Point, button: Int) {
    if (NEIRecipeProxy.hasNei)
      NEIRecipeProxy.openRecipes(recipeid)
  }

  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) {
    super.handleTooltip(p, tip)
    if (NEIRecipeProxy.hasNei) tip += Misc.toLocal("hamcraft.label.recipes")
  }
}
