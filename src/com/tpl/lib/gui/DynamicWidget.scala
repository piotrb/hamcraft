package com.tpl.lib.gui

import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.Widget
import scala.collection.mutable

abstract class DynamicWidget(bgTexture: Texture, fgOffset: Rect) extends Widget {

  val widget: Widget

  val fgRect = Util.fgRect(rect, fgOffset)

  override def draw(mouse: Point) {
    widget.parent = parent
    widget.draw(mouse)
  }

  override def drawBackground(mouse: Point) {
    parent.drawTexture(rect, bgTexture)
  }

  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) = widget.handleTooltip(p, tip)

}
