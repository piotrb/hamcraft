package com.tpl.hamcraft.config.loader

import com.tpl.hamcraft.config.Tuning
import net.bdew.lib.recipes.gencfg.GenericConfigLoader
import net.bdew.lib.recipes.{RecipeLoader, RecipeParser}

class Loader extends RecipeLoader with GenericConfigLoader {
  val cfgStore = Tuning

  override def newParser(): RecipeParser = new Parser()
}
