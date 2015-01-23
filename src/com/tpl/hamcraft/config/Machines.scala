package com.tpl.hamcraft.config

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.machines.feedmixer.MachineFeedMixer
import com.tpl.hamcraft.machines.milker.MachineMilker
import com.tpl.hamcraft.misc.HamcraftCreativeTabs
import net.bdew.lib.config.MachineManager

object Machines extends MachineManager(Tuning.getSection("Machines"), Config.guiHandler, HamcraftCreativeTabs.main) {
  //  registerMachine(MachineBreeder)
  registerMachine(MachineFeedMixer)
  //    registerMachine( MachineIncubator)
  registerMachine(MachineMilker)

  HamCraftMod.logInfo("Machines loaded")
}
