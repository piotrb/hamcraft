package com.tpl.hamcraft.config

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.items.{EmancipatedAnimal, Emancipator}
import com.tpl.hamcraft.misc.HamcraftCreativeTabs
import net.bdew.lib.config.ItemManager

object Items extends ItemManager(HamcraftCreativeTabs.main) {
  regItem(Emancipator)
  regItem(EmancipatedAnimal)

  HamCraftMod.logInfo("Items loaded")
}
