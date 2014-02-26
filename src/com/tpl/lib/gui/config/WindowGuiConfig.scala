package com.tpl.lib.gui.config

class WindowGuiConfig(data: Map[String, Any], textures: TextureGuiConfig) extends MapDataAccess {
  val size = getSize(data, "size")
  val texture = textures.getTexture(getString(data, "texture"))
}
