package com.tpl.lib.gui.config

import java.net.URL

import com.typesafe.config.{Config, ConfigFactory}

//trait ConfigAccess {
//  var config: Config
//}

object ConfigFile {

  def loadConfig(name: String, modId: String): Config = {
    val res = "/assets/%s/gui/%s.conf".format(modId, name)
    val resource: URL = this.getClass.getResource(res)
    if(resource == null) throw new RuntimeException(s"could not find resource matching: $res")
    ConfigFactory.parseURL(resource)
  }

}
