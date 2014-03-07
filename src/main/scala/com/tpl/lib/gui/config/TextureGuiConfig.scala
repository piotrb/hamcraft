package com.tpl.lib.gui.config

import net.bdew.lib.gui.{Texture}

class TextureGuiConfig(data: Map[String, Any], modId: String) extends MapDataAccess {
  def getTexture(key: String) = {
    val info = data(key).asInstanceOf[Map[String, Any]]
    val rect = getRect(info, "rect")
    val file = getString(info, "file")
    val scale = getInt(info, "scale", Some(256.0))

    Texture.apply(modId + ":textures/gui/" + file, rect, scale)
  }
}
