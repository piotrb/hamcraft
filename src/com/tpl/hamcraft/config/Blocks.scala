package com.tpl.hamcraft.config

import net.bdew.lib.config.BlockManager
import com.tpl.hamcraft.HamCraftMod

object Blocks extends BlockManager(Config.IDs) {
//  val mutagen = regBlockCls(classOf[BlockMutagen], "Mutagen")

  HamCraftMod.logInfo("Blocks loaded")
}
