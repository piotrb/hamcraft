package com.tpl.hamcraft.config

import net.bdew.lib.config.FluidManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.fluids.babyfood.FluidBabyFood

object Fluids extends FluidManager(Config.IDs) {
  val babyfood: FluidBabyFood = regFluid(new FluidBabyFood)
  HamCraftMod.logInfo("Fluids loaded")
}
