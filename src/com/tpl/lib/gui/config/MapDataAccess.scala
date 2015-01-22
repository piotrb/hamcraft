package com.tpl.lib.gui.config

import net.bdew.lib.gui.{Color, Point, Rect}
import com.tpl.lib.gui.ObjectDumping

trait MapDataAccess extends ObjectDumping {
  protected def getString(data: Map[String, Any], key: String, default: Option[String] = None) = {
    getKey(data, key, default).asInstanceOf[String]
  }

  protected def getPoint(data: Map[String, Any], key: String, default: Option[Point] = None) = {
    val info = getKey(data, key, default).asInstanceOf[Map[String, Double]]
    Point(info("x").toFloat, info("y").toFloat)
  }

  protected def getRect(data: Map[String, Any], key: String, default: Option[Rect] = None) = {
    val info = getKey(data, key, default).asInstanceOf[Map[String, Double]]
    Rect(info("x").toFloat, info("y").toFloat, info("w").toFloat, info("h").toFloat)
  }

  protected def getColor(data: Map[String, Any], key: String, default: Option[Color] = None) = {
    val info = getKey(data, key, default).asInstanceOf[List[Double]]
    new Color(info(0).toFloat, info(1).toFloat, info(2).toFloat, info(3).toFloat)
  }

  protected def getInt(data: Map[String, Any], key: String, default: Option[Double] = None) = {
    getKey(data, key, default).asInstanceOf[Double].toInt
  }

  private def getKey(data: Map[String, Any], key: String, default: Option[Any] = None) = {
    val info = data.get(key)
    if(info.isEmpty && default.isEmpty) throw new Exception(String.format("could not find key %s in %s", key, dump(data)))
    if(info.isEmpty) default.get else info.get
  }
}
