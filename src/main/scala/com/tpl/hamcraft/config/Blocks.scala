package com.tpl.hamcraft.config

import net.bdew.lib.config.BlockManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.fluids.babyfood.BlockBabyFood

object Blocks extends BlockManager(Config.IDs) {
  val babyfood = regBlockCls(classOf[BlockBabyFood], "BabyFood")

  HamCraftMod.logInfo("Blocks loaded")
}
