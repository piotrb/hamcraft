package com.tpl.lib.gui.config

import com.tpl.lib.gui.WidgetPlayerInventory
import com.typesafe.config.Config
import net.bdew.lib.gui.{Point, Color}

class PlayerInventoryContainerInfo(val x: Int, val y: Int, val hotbarY: Int) {}

trait PlayerInventoryLoading extends MapDataAccess {
  val widgets: Config
  val textures: TextureGuiConfig

  def getPlayerInventoryWidget(name: String) = {
    getOptionLeaf(widgets, name, None, (info: Config) => {
      val config = new PlayerInventoryWidgetConfig(info, textures)
      new WidgetPlayerInventory(config.origin.x.toInt, config.origin.y.toInt, config.texture, config.invColor, config.hotbarColor, config.hotbarSpace, config.cellSize)
    })
  }

  def getPlayerInventoryContainerInfo(name: String) = {
    getOptionLeaf(widgets, name, None, (info: Config) => {
      val config = new ServerInventoryWidgetConfig(info, null)
      new PlayerInventoryContainerInfo((config.origin.x + config.fgOffset.x).toInt, (config.origin.y + config.fgOffset.y).toInt, (config.origin.y + 18*3 + config.hotbarSpace + config.fgOffset.y).toInt)
    })
  }

  class ServerInventoryWidgetConfig(data: Config, textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetPlayerInventory") {
    val origin = getPoint(data, "origin")
    val fgOffset = getPoint(data, "fgOffset", Some(Point(1,1)))
    val hotbarSpace = getInt(data, "hotbarSpace", Some(4))
    val cellSize = getInt(data, "cellSize", Some(18))
  }

  class PlayerInventoryWidgetConfig(data: Config, textures: TextureGuiConfig) extends ServerInventoryWidgetConfig(data, textures) {
    val texture = getTexture("texture")
    val invColor = getColor(data, "invColor", Some(Color.white))
    val hotbarColor = getColor(data, "hotbarColor", Some(Color.white))
  }
}
