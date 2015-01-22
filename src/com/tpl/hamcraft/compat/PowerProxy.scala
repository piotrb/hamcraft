package com.tpl.hamcraft.compat

import cpw.mods.fml.common.{ModAPIManager, ModContainer, Loader}
import com.tpl.hamcraft.HamCraftMod
import java.util
import com.tpl.hamcraft.config.Tuning

object PowerProxy {
  final val IC2_MOD_ID = "IC2"
  final val BC_MOD_ID = "BuildCraftAPI|power"
  final val TE_MOD_ID = "CoFHCore"

  lazy val EUEnabled = Tuning.getSection("Power").getSection("EU").getBoolean("Enabled")
  lazy val MJEnabled = Tuning.getSection("Power").getSection("BC").getBoolean("Enabled")
  lazy val RFEnabled = Tuning.getSection("Power").getSection("RF").getBoolean("Enabled")

  lazy val lookup: collection.Map[String, ModContainer] = {
    val mods = new util.ArrayList[ModContainer]
    val nameLookup = new util.HashMap[String, ModContainer]

    nameLookup.putAll(Loader.instance().getIndexedModList)
    ModAPIManager.INSTANCE.injectAPIModContainers(mods, nameLookup)

    import scala.collection.JavaConverters._
    nameLookup.asScala
  }

  lazy val haveBC = haveModVersion(BC_MOD_ID)
  lazy val haveIC2 = haveModVersion(IC2_MOD_ID)
  lazy val haveTE = haveModVersion(TE_MOD_ID)

  def haveModVersion(modid: String) = lookup.contains(modid)

  def getModVersion(modid: String): String = {
    val cont = lookup.getOrElse(modid, return "NOT FOUND")
    cont.getModId + " " + cont.getVersion
  }

  def logModVersions() {
    if (!haveBC && !haveIC2 && !haveTE) {
      HamCraftMod.logWarn("No useable energy system detected")
      HamCraftMod.logWarn("This mod requires at least one of the following mods to function properly:")
      HamCraftMod.logWarn("* BuildCraft 4.2.0+ or any mod that properly bundles the BC API")
      HamCraftMod.logWarn("* IC2 Experimental")
      HamCraftMod.logWarn("* CoFHCore (Thermal Expansion)")
    }
    HamCraftMod.logInfo("BC API Version: %s", getModVersion(BC_MOD_ID))
    HamCraftMod.logInfo("IC2 Version: %s", getModVersion(IC2_MOD_ID))
    HamCraftMod.logInfo("TE(CoFHCore) Version: %s", getModVersion(TE_MOD_ID))
  }
}
