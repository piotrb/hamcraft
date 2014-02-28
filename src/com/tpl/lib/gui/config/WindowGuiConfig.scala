package com.tpl.lib.gui.config

import net.bdew.lib.gui.{ScaledResourceLocation, Texture}
import com.tpl.lib.gui.GuiTextureLocation

class WindowGuiConfig(data: Map[String, Any], textures: TextureGuiConfig, modId: String) extends MapDataAccess {
  val rect = getRect(data, "rect")
  val mode = getString(data, "mode")

  def dynamicGuiTexture = {
    val info = data("dynamicBackground").asInstanceOf[Map[String, Any]]
    val textureRect = getRect(info, "rect")
    val tileSize = getInt(info, "tileSize", Some(4.0))
    val resource = new ScaledResourceLocation(modId, "textures/gui/" + getString(info, "file"), getInt(info, "scale", Some(256.0)))
    new GuiTextureLocation(resource, textureRect.origin, tileSize)
  }
}
