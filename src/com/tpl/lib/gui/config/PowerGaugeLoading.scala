package com.tpl.lib.gui.config

import com.tpl.lib.gui.{ObjectDumping, WidgetPowerGaugeDynamic}
import com.typesafe.config.Config
import net.bdew.lib.Misc
import net.bdew.lib.gui.widgets.WidgetLabel
import net.bdew.lib.power.DataSlotPower

trait PowerGaugeLoading extends MapDataAccess with ObjectDumping {

  val widgets: Config
  val textures: TextureGuiConfig

  def getPowerGauge(name: String, dslot: DataSlotPower, widgetClass: Class[_ <: WidgetPowerGaugeDynamic] = classOf[WidgetPowerGaugeDynamic]) = {
    getOptionLeaf(widgets, name, None, (info: Config) => {
      val config = new PowerGaugelWidgetConfig(info, textures)
      val constructor = widgetClass.getConstructors.headOption
      if(!constructor.isDefined) throw new Exception("could not find constructor to match: " + dump(info))
      constructor.get.newInstance(config.rect, config.fgTexture, dslot, config.bgTexture, config.fgOffset).asInstanceOf[WidgetPowerGaugeDynamic]
    })
  }

  class PowerGaugelWidgetConfig(data: Config, textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetPowerGaugeDynamic") {
    val rect = getRect(data, "rect")
    val fgOffset = getRect(data, "fgOffset")
    val bgTexture = getTexture("bgTexture")
    val fgTexture = getTexture("fgTexture")
  }

}
