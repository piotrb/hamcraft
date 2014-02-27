package com.tpl.lib.gui.config

import net.bdew.lib.gui.{Color, Point, Rect}
import cpw.mods.fml.common.FMLLog

trait MapDataAccess {
  protected def getString(data: Map[String, Any], key: String) = {
    data(key).asInstanceOf[String]
  }

  protected def getPoint(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[Map[String, Double]]
    Point(info("x").toFloat, info("y").toFloat)
  }

  protected def getRect(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[Map[String, Double]]
    Rect(info("x").toFloat, info("y").toFloat, info("w").toFloat, info("h").toFloat)
  }

  protected def getColor(data: Map[String, Any], key: String) = {
    val info = data(key).asInstanceOf[List[Double]]
    new Color(info(0).toFloat, info(1).toFloat, info(2).toFloat, info(3).toFloat)
  }

  def getInt(data: Map[String, Any], key: String) = {
    data(key).asInstanceOf[Double].toInt
  }
}
