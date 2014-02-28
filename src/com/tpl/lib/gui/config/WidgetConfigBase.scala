package com.tpl.lib.gui.config

import net.bdew.lib.gui.{Texture, Point, Color}

class WidgetConfigBase(data: Map[String, Any], textures: TextureGuiConfig, expectedType: String) extends MapDataAccess {
  validateType

  def getTexture(key: String): Texture = textures.getTexture(getString(data, key))

  private def validateType {
    if(getString(data, "type") != expectedType) {
      throw new Exception(String.format("type mismatch: expected %s, got %s", getString(data, "type"), expectedType))
    }
  }
}
