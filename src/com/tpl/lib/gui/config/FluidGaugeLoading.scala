package com.tpl.lib.gui.config

import com.tpl.lib.gui.{WidgetFluidGaugeDynamic, ObjectDumping}
import com.typesafe.config.Config
import net.bdew.lib.data.DataSlotTankBase

trait FluidGaugeLoading extends ObjectDumping with MapDataAccess {

  val widgets: Config
  val textures: TextureGuiConfig

  def getFluidGauge(name: String, dslot: DataSlotTankBase, widgetClass: Class[_ <: WidgetFluidGaugeDynamic] = classOf[WidgetFluidGaugeDynamic]) = {
    getOptionLeaf(widgets, name, None, (info: Config) => {
      val config = new FluidGaugelWidgetConfig(info, textures)
      val constructor = widgetClass.getConstructors.headOption
      if(!constructor.isDefined) throw new Exception("could not find constructor to match: " + dump(info))
      constructor.get.newInstance(config.rect, config.fgTexture, dslot, config.bgTexture, config.fgOffset).asInstanceOf[WidgetFluidGaugeDynamic]
    })
  }

  class FluidGaugelWidgetConfig(data: Config, textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetFluidGaugeDynamic") {
    val rect = getRect(data, "rect")
    val fgOffset = getRect(data, "fgOffset")
    val bgTexture = getTexture("bgTexture")
    val fgTexture = getTexture("fgTexture")
  }

}
