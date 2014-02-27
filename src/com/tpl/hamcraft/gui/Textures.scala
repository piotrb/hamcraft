package com.tpl.hamcraft.gui

import com.tpl.hamcraft.HamCraftMod
import net.bdew.lib.gui._

object Textures {
  val texture = new ScaledResourceLocation(HamCraftMod.modId + ":textures/gui/widgets.png", 256)

  val tankOverlay: Texture = Texture.apply(texture, 16, 0, 16, 58)
  val powerFill: Texture = Texture.apply(texture, 0, 0, 16, 58)

  def whiteProgress(width: Int): Texture = Texture.apply(texture, 136 - width, 73, 137, 15)
}
