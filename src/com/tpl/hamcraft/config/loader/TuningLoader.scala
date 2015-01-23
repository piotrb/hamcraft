package com.tpl.hamcraft.config.loader

import java.io._

import com.tpl.hamcraft.HamCraftMod
import net.bdew.lib.recipes.RecipesHelper

object TuningLoader {

  val loader = new Loader

  def loadDelayed() = loader.processRecipeStatements()

  def loadConfigFiles() {

    if (!HamCraftMod.configDir.exists()) {
      HamCraftMod.configDir.mkdir()
      val nl = System.getProperty("line.separator")
      val f = new FileWriter(new File(HamCraftMod.configDir, "readme.txt"))
      f.write("Any .cfg files in this directory will be loaded after the internal configuration, in alphabetic order" + nl)
      f.write("Files in 'overrides' directory with matching names can be used to override internal configuration" + nl)
      f.close()
    }

    RecipesHelper.loadConfigs(
      modName = "HamCraft",
      listResource = "/assets/hamcraft/config/files.lst",
      configDir = HamCraftMod.configDir,
      resBaseName = "/assets/hamcraft/config/",
      loader = loader)
  }
}
