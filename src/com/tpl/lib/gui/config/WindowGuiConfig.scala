package com.tpl.lib.gui.config

import com.tpl.lib.gui.GuiTextureLocation
import com.typesafe.config.Config
import net.bdew.lib.gui.ScaledResourceLocation

class WindowGuiConfig(data: Config, textures: TextureGuiConfig, modId: String) extends MapDataAccess {
  val rect = getRect(data, "rect")
  val mode = getString(data, "mode")

  def dynamicGuiTexture: GuiTextureLocation = {
    getOptionLeaf(data, "dynamicBackground", None, (info: Config) => {
      val textureRect = getRect(info, "rect")
      val tileSize = getInt(info, "tileSize", Some(4))
      val resource = new ScaledResourceLocation(modId, "textures/gui/" + getString(info, "file"), getInt(info, "scale", Some(256)))

      new GuiTextureLocation(resource, textureRect.origin, tileSize)
    })
  }
}
