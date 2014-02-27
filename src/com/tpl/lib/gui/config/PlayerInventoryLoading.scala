package com.tpl.lib.gui.config

import com.tpl.lib.gui.WidgetPlayerInventory

class PlayerInventoryContainerInfo(val x: Int, val y: Int, val hotbarY: Int) {}

trait PlayerInventoryLoading {
  val widgets: Map[String, Any]
  val textures: TextureGuiConfig

  def getPlayerInventoryWidget(name: String) = {
    val info = widgets(name).asInstanceOf[Map[String, Any]]
    val config = new PlayerInventoryWidgetConfig(info, textures)
    new WidgetPlayerInventory(config.origin.x.toInt, config.origin.y.toInt, config.texture, config.invColor, config.hotbarColor)
  }

  def getPlayerInventoryContainerInfo(name: String) = {
    val info = widgets(name).asInstanceOf[Map[String, Any]]
    val config = new PlayerInventoryWidgetConfig(info, textures)
    new PlayerInventoryContainerInfo((config.origin.x + 1).toInt, (config.origin.y + 1).toInt, (config.origin.y + 18*3 + 4 + 1).toInt)
  }

  class PlayerInventoryWidgetConfig(data: Map[String, Any], textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetPlayerInventory") {
    val origin = getPoint("origin")
    val texture = getTexture("texture")
    val invColor = getColor("invColor")
    val hotbarColor = getColor("hotbarColor")
  }
}
