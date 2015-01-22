package com.tpl.lib.gui.config

import com.tpl.lib.gui.{WidgetFluidGaugeDynamic, ObjectDumping}
import net.bdew.lib.data.DataSlotTankBase

trait FluidGaugeLoading extends ObjectDumping {

  val widgets: Map[String, Any]
  val textures: TextureGuiConfig

  def getFluidGauge(name: String, dslot: DataSlotTankBase, widgetClass: Class[_ <: WidgetFluidGaugeDynamic] = classOf[WidgetFluidGaugeDynamic]) = {
    val info = widgets(name).asInstanceOf[Map[String, Any]]
    val config = new FluidGaugelWidgetConfig(info, textures)
    val constructor = widgetClass.getConstructors.headOption
    if(!constructor.isDefined) throw new Exception("could not find constructor to match: " + dump(info))
    constructor.get.newInstance(config.rect, config.fgTexture, dslot, config.bgTexture, config.fgOffset).asInstanceOf[WidgetFluidGaugeDynamic]
  }

  class FluidGaugelWidgetConfig(data: Map[String, Any], textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetFluidGaugeDynamic") {
    val rect = getRect(data, "rect")
    val fgOffset = getRect(data, "fgOffset")
    val bgTexture = getTexture("bgTexture")
    val fgTexture = getTexture("fgTexture")
  }

}
