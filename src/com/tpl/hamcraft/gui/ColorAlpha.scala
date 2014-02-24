package com.tpl.hamcraft.gui

import net.bdew.lib.gui.Color
import org.lwjgl.opengl.GL11

class ColorAlpha(r: Float, g: Float, b: Float, a: Float) extends Color(r,g,b) {
  override def activate() {
    GL11.glColor4f(r, g, b, a)
  }
}
