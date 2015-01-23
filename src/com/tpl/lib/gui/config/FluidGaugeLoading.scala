//package com.tpl.lib.gui.config
//
//import com.tpl.lib.gui.{WidgetFluidGaugeDynamic, ObjectDumping}
//import net.bdew.lib.data.DataSlotTankBase
//
//trait FluidGaugeLoading extends ObjectDumping {
//
//  val widgets: ConfigFileLeafBase
//  val textures: TextureGuiConfig
//
//  def getFluidGauge(name: String, dslot: DataSlotTankBase, widgetClass: Class[_ <: WidgetFluidGaugeDynamic] = classOf[WidgetFluidGaugeDynamic]) = {
//    val info = widgets.getLeaf(name).get
//    val config = new FluidGaugelWidgetConfig(info, textures)
//    val constructor = widgetClass.getConstructors.headOption
//    if(!constructor.isDefined) throw new Exception("could not find constructor to match: " + dump(info))
//    constructor.get.newInstance(config.rect, config.fgTexture, dslot, config.bgTexture, config.fgOffset).asInstanceOf[WidgetFluidGaugeDynamic]
//  }
//
//  class FluidGaugelWidgetConfig(data: ConfigFileLeafBase, textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetFluidGaugeDynamic") {
//    val rect = data.getRect("rect")
//    val fgOffset = data.getRect("fgOffset")
//    val bgTexture = data.getTexture("bgTexture")
//    val fgTexture = data.getTexture("fgTexture")
//  }
//
//}
