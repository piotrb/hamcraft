package com.tpl.hamcraft

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.network.{NetworkRegistry, NetworkMod}
import java.util.logging.Logger
import java.io.File
import com.tpl.hamcraft.config._
import com.tpl.hamcraft.compat.PowerProxy

@Mod(modid = "HamCraft", name = "H-A-M Craft", version = "MOD_VERSION", modLanguage = "scala", dependencies = "required-after:ThermalExpansion@(3.0,);required-after:bdlib@(1.0,);required-after:Forestry")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
object HamCraftMod {
  var log: Logger = null
  var instance = this

  final val modId = "hamcraft"
  final val channel = "com.tpl.hamcraft"

  var configDir: File = null

  def logInfo(msg: String, args: Any*) = log.info(msg.format(args: _*))
  def logWarn(msg: String, args: Any*) = log.warning(msg.format(args: _*))

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) {
    log = event.getModLog
    PowerProxy.logModVersions()
    configDir = event.getModConfigurationDirectory
    TuningLoader.load("tuning")
    TuningLoader.load("recipes")
    TuningLoader.load("override", checkJar = false)
    Config.load(event.getSuggestedConfigurationFile)
  }

  @EventHandler
  def init(event: FMLInitializationEvent) {
    NetworkRegistry.instance.registerGuiHandler(this, Config.guiHandler)
    Util.registerOreDictionary()
//    Upgrades.init()
    TuningLoader.loadDelayed()
  }

  @EventHandler
  def postInit(event: FMLPostInitializationEvent) {
  }

}
