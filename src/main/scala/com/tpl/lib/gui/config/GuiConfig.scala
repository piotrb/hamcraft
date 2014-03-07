package com.tpl.lib.gui.config

class GuiConfig(name: String, modId: String)
  extends LabelWidgetLoading
  with PlayerInventoryLoading
  with PowerGaugeLoading
  with FluidGaugeLoading
  with JsonLoading
{
  loadConfig(name, modId)

  val widgets = data("widgets").asInstanceOf[Map[String, Any]]

  val textures = new TextureGuiConfig(modId)

  val window = new WindowGuiConfig(data("window").asInstanceOf[Map[String, Any]], textures, modId)

}
