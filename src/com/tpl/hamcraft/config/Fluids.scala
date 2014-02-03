package com.tpl.hamcraft.config

import net.bdew.lib.config.FluidManager
import com.tpl.hamcraft.HamCraftMod

object Fluids extends FluidManager(Config.IDs) {
//  val mutagen: FluidMutagen = regFluid(new FluidMutagen)
  HamCraftMod.logInfo("Fluids loaded")
}
