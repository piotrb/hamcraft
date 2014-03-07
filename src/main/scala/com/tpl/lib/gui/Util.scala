package com.tpl.lib.gui

import net.bdew.lib.gui._

object Util {
  def fgRect(bgRect: Rect, fgOffset: Rect) = Rect(bgRect.x+fgOffset.x, bgRect.y+fgOffset.y, bgRect.w+fgOffset.w, bgRect.h+fgOffset.h)
}
