package com.tpl.lib.gui.config

import net.minecraft.util.ResourceLocation
import net.bdew.lib.gui.TextureLocation

class TextureGuiConfig(data: Map[String, Any], modId: String) extends MapDataAccess {
  def getTexture(key: String) = {
    val info = data(key).asInstanceOf[Map[String, Any]]
    val origin = getPoint(info, "origin")
    val file = getString(info, "file")

    val texture = new ResourceLocation(modId + ":textures/gui/" + file)
    new TextureLocation(texture, origin)
  }
}
