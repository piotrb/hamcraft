package com.tpl.lib.gui.config

import com.typesafe.config.Config
import net.bdew.lib.gui.Texture

class WidgetConfigBase(data: Config, textures: TextureGuiConfig, expectedType: String) extends MapDataAccess {
  validateType()

  def getTexture(key: String): Texture = {
    textures.getTexture(getString(data, key))
  }

  private def validateType() {
    if(getString(data, "type") != expectedType) {
      throw new Exception(String.format("type mismatch: expected %s, got %s", getString(data, "type"), expectedType))
    }
  }
}
