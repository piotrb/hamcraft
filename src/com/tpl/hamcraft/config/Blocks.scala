package com.tpl.hamcraft.config

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.misc.HamcraftCreativeTabs
import net.bdew.lib.config.BlockManager

object Blocks extends BlockManager(HamcraftCreativeTabs.main) {
  HamCraftMod.logInfo("Blocks loaded")
}
