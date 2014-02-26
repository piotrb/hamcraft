package com.tpl.lib.gui.config

import scala.util.parsing.json.JSON

class GuiConfig(name: String, modId: String)
  extends LabelWidgetLoading
  with PlayerInventoryLoading
{
  var data: Map[String, Any] = null

  loadConfig(name)

  val widgets = data("widgets").asInstanceOf[Map[String, Any]]

  val textures = new TextureGuiConfig(data("textures").asInstanceOf[Map[String, Any]], modId)
  val window = new WindowGuiConfig(data("window").asInstanceOf[Map[String, Any]], textures)

  private def loadConfig(name: String) {
    val res = "/assets/%s/gui/%s.json".format(modId, name)
    val stream = this.getClass.getResourceAsStream(res)
    val json = scala.io.Source.fromInputStream(stream).getLines().mkString("\n")
    data = JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
  }

}
