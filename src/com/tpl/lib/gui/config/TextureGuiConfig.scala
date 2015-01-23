package com.tpl.lib.gui.config

import com.typesafe.config.Config
import net.bdew.lib.gui.Texture

class TextureGuiConfig(modId: String) extends MapDataAccess {
  var config = ConfigFile.loadConfig("textures", modId)

  var textureData = config.getConfig("textures")

  def getTexture(key: String) = {
    getOptionLeaf(textureData, key, None, (info: Config) => {
      val rect = getRect(info, "rect")
      val file = getString(info, "file")
      val scale = getInt(info, "scale", Some(256))
      Texture.apply(modId + ":textures/gui/" + file, rect, scale)
    })
  }
}
