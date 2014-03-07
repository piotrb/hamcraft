package com.tpl.lib.gui.config

import net.bdew.lib.gui.widgets.WidgetLabel
import net.bdew.lib.Misc
import net.bdew.lib.gui.Color

trait LabelWidgetLoading {

  val widgets: Map[String, Any]
  val textures: TextureGuiConfig

  def getLabelWidget(name: String) = {
    val info = widgets(name).asInstanceOf[Map[String, Any]]
    val config = new LabelWidgetConfig(info, textures)
    new WidgetLabel(Misc.toLocal(config.label), config.origin.x.toInt, config.origin.y.toInt, config.color)
  }

  class LabelWidgetConfig(data: Map[String, Any], textures: TextureGuiConfig) extends WidgetConfigBase(data, textures, "WidgetLabel") {
    val label = getString(data, "label")
    val origin = getPoint(data, "origin")
    val color = getColor(data, "color", Some(new Color(0.25F, 0.25F, 0.25F)))
  }

}
