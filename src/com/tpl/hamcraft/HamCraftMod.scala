package com.tpl.hamcraft

import java.io.File

import com.tpl.hamcraft.compat.PowerProxy
import com.tpl.hamcraft.config._
import com.tpl.hamcraft.config.loader._
import com.tpl.hamcraft.misc._
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import org.apache.logging.log4j.Logger

@Mod(modid = "HamCraft", name = "H-A-M Craft", version = "MOD_VERSION", modLanguage = "scala", dependencies = "required-after:bdlib@(1.6,);required-after:Forestry")
object HamCraftMod {
  final val modId = "hamcraft"
  final val channel = "com.tpl.hamcraft"
  var log: Logger = null
  var instance = this
  var configDir: File = null

  def logInfo(msg: String, args: Any*) = log.info(msg.format(args: _*))

  def logWarn(msg: String, args: Any*) = log.warn(msg.format(args: _*))

  def logError(msg: String, args: Any*) = log.error(msg.format(args: _*))

  def logWarnException(msg: String, t: Throwable, args: Any*) = log.warn(msg.format(args: _*), t)

  def logErrorException(msg: String, t: Throwable, args: Any*) = log.error(msg.format(args: _*), t)

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) {
    log = event.getModLog
    configDir = new File(event.getModConfigurationDirectory, "hamcraft")

    TuningLoader.loadConfigFiles()
    Config.load()

    PowerProxy.logModVersions()
  }

  @EventHandler
  def init(event: FMLInitializationEvent) {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, Config.guiHandler)
    //    Util.registerOreDictionary()
    //    Upgrades.init()
    TuningLoader.loadDelayed()
  }

  @EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = {
    HamcraftCreativeTabs.init()
  }

}
