package com.tpl.hamcraft.config

import net.bdew.lib.config.MachineManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.machines.breeder.MachineBreeder
import com.tpl.hamcraft.machines.feedmixer.MachineFeedMixer
import com.tpl.hamcraft.machines.incubator.MachineIncubator

object Machines extends MachineManager(Config.IDs, Tuning.getSection("Machines"), Config.guiHandler) {
  val breeder = registerMachine(new MachineBreeder)
  val feedmixer = registerMachine(new MachineFeedMixer)
  val incubator = registerMachine(new MachineIncubator)

  HamCraftMod.logInfo("Machines loaded")
}
