package com.tpl.lib.gui.config

import net.bdew.lib.gui.{Color, Point}
import com.tpl.lib.gui.Size
import cpw.mods.fml.common.FMLLog

trait MapDataAccess {
  protected def getString(data: Map[String, Any], key: String) = {
    data(key).asInstanceOf[String]
  }

  protected def getPoint(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[Map[String, Double]]
    new Point(info("x").toInt, info("y").toInt)
  }

  protected def getSize(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[Map[String, Double]]
    new Size(info("w").toInt, info("h").toInt)
  }

  protected def getColor(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[List[Double]]
    new Color(info(0).toFloat, info(1).toFloat, info(2).toFloat, info(3).toFloat)
  }

  def getInt(data: Map[String, Any], key: String) = {
    data(key).asInstanceOf[Double].toInt
  }
}
