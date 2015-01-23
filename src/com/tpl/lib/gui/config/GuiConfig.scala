package com.tpl.lib.gui.config

class GuiConfig(name: String, modId: String)
  extends LabelWidgetLoading
  with PlayerInventoryLoading
  with PowerGaugeLoading
//  with FluidGaugeLoading
//  with ConfigAccess
{
  var config = ConfigFile.loadConfig(name, modId)

  val widgets = config.getConfig("widgets")

  val textures = new TextureGuiConfig(modId)

  val window = new WindowGuiConfig(config.getConfig("window"), textures, modId)
}
