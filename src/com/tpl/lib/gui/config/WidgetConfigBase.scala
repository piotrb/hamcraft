package com.tpl.lib.gui.config

import net.bdew.lib.gui.{TextureLocation, Point, Color}

class WidgetConfigBase(data: Map[String, Any], textures: TextureGuiConfig, expectedType: String) extends MapDataAccess {
  validateType

  def getString(key: String): String = getString(data, key)
  def getPoint(key: String): Point = getPoint(data, key)
  def getColor(key: String): Color = getColor(data, key)
  def getInt(key: String): Int = getInt(data, key)
  def getTexture(key: String): TextureLocation = textures.getTexture(data(key).asInstanceOf[String])

  private def validateType {
    if(getString("type") != expectedType) {
      throw new Exception(String.format("type mismatch: expected %s, got %s", getString("type"), expectedType))
    }
  }
}
