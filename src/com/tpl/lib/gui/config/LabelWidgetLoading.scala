package com.tpl.lib.gui.config

import net.bdew.lib.gui.widgets.WidgetLabel
import net.bdew.lib.Misc

trait LabelWidgetLoading {

  val widgets: Map[String, Any]
  val textures: TextureGuiConfig

  def getLabelWidget(name: String) = {
    val info = widgets(name).asInstanceOf[Map[String, Any]]
    val config = new LabelWidgetConfig(info, textures)
    new WidgetLabel(Misc.toLocal(config.label), config.origin.x.toInt, config.origin.y.toInt, config.color)
  }

  class LabelWidgetConfig(data: Map[String, Any], textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetLabel") {
    val label = getString("label")
    val origin = getPoint("origin")
    val color = getColor("color")
  }

}
