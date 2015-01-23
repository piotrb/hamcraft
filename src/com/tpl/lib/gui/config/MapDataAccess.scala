package com.tpl.lib.gui.config

import com.typesafe.config.{Config, ConfigException}
import net.bdew.lib.gui.{Color, Point, Rect}

trait MapDataAccess {
  protected def getRect(data: Config, key: String, default: Option[Rect] = None): Rect = {
    getOptionLeaf(data, key, default, (info: Config) => {
      Rect(getFloat(info, "x"), getFloat(info, "y"), getFloat(info, "w"), getFloat(info, "h"))
    })
  }

  protected def getInt(data: Config, key: String, default: Option[Int] = None): Int = {
    withDefault(default, () => {
      data.getInt(key)
    })
  }

  protected def getPoint(data: Config, key: String, default: Option[Point] = None): Point = {
    getOptionLeaf(data, key, default, (info: Config) => {
      Point(getFloat(info, "x"), getFloat(info, "y"))
    })
  }

  protected def getFloat(data: Config, key: String, default: Option[Float] = None): Float = {
    withDefault(default, () => {
      data.getDouble(key).toFloat
    })
  }

  protected def getString(data: Config, key: String, default: Option[String] = None): String = {
    withDefault(default, () => {
      data.getString(key)
    })
  }

  protected def getColor(data: Config, key: String, default: Option[Color] = None): Color = {
    getOptionLeaf(data, key, default, (info: Config) => {
      new Color(getFloat(info, "r"), getFloat(info, "g"), getFloat(info, "b"), getFloat(info, "a", Some(1.0F)))
    })
  }

  protected def getOptionLeaf[T](data: Config, key: String, default: Option[T] = None, block: Config => T): T = {
    withDefault(default, () => {
      block(data.getConfig(key))
    })
  }

  private def withDefault[T](default: Option[T], block: () => T): T = {
    try {
      block()
    }
    catch {
      case e: ConfigException.Missing =>
        if (default.isDefined) {
          default.get
        } else {
          throw e
        }
    }
  }

}
