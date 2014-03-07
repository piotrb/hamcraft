package com.tpl.lib.gui.config

import scala.util.parsing.json.JSON

trait JsonLoading {

  var data: Map[String, Any] = null

  protected def loadConfig(name: String, modId: String) {
    val res = "/assets/%s/gui/%s.json".format(modId, name)
    val stream = this.getClass.getResourceAsStream(res)
    val json = scala.io.Source.fromInputStream(stream).getLines().mkString("\n")
    data = JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
  }

}
