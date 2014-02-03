package com.tpl.hamcraft.config

import net.bdew.lib.config.MachineManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.machines.breeder.MachineBreeder

object Machines extends MachineManager(Config.IDs, Tuning.getSection("Machines"), Config.guiHandler) {
  val breeder = registerMachine(new MachineBreeder)
//  val mutagenProducer = registerMachine(new MachineMutagenProducer)
//  val mutatron = registerMachine(new MachineMutatron)
//  val apiary = registerMachine(new MachineApiary)
//  val imprinter = registerMachine(new MachineImprinter)
//  val sampler = registerMachine(new MachineSampler)
//  val mutatronAdv = registerMachine(new MachineMutatronAdv)

  HamCraftMod.logInfo("Machines loaded")
}
