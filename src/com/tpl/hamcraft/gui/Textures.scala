package com.tpl.hamcraft.gui

import net.minecraft.util.ResourceLocation
import net.bdew.lib.gui.TextureLocation
import com.tpl.hamcraft.HamCraftMod

object Textures {
  val texture = new ResourceLocation(HamCraftMod.modId + ":textures/gui/widgets.png")
  val tankOverlay = new TextureLocation(texture, 16, 0)
  val powerFill = new TextureLocation(texture, 0, 0)
  val texturePowerError = new TextureLocation(texture, 32, 0)
  val slotSelect = new TextureLocation(texture, 48, 0)
  def greenProgress(width: Int) = new TextureLocation(texture, 136 - width, 58)
  def whiteProgress(width: Int) = new TextureLocation(texture, 136 - width, 73)
}
