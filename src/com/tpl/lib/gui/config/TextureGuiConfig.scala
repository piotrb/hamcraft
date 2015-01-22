package com.tpl.lib.gui.config

import net.bdew.lib.gui.Texture

class TextureGuiConfig(modId: String)
  extends MapDataAccess
  with JsonLoading
{
  loadConfig("textures", modId)

  var textureData = data("textures").asInstanceOf[Map[String, Any]]

  def getTexture(key: String) = {
    val info = textureData(key).asInstanceOf[Map[String, Any]]
    val rect = getRect(info, "rect")
    val file = getString(info, "file")
    val scale = getInt(info, "scale", Some(256.0))

    Texture.apply(modId + ":textures/gui/" + file, rect, scale)
  }
}
