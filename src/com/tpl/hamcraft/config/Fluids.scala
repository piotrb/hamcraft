package com.tpl.hamcraft.config

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.fluids.{BlockFluid, ItemFluidBucket, ItemFluidCan}
import com.tpl.hamcraft.forestry.ForestryItems
import com.tpl.hamcraft.misc.HamcraftCreativeTabs
import cpw.mods.fml.common.registry.GameRegistry
import net.bdew.lib.config.FluidManager
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.{Fluid, FluidContainerRegistry, FluidRegistry, FluidStack}

object Fluids extends FluidManager() {

  val emptyBucket = new ItemStack(GameRegistry.findItem("minecraft", "bucket"))
  val emptyCan = new ItemStack(ForestryItems.canEmpty)
  val babyfood = registerFluid("BabyFood")

  def registerFluid(id: String,
                    luminosity: Int = 0,
                    density: Int = 1000,
                    temperature: Int = 295,
                    viscosity: Int = 1000,
                    isGaseous: Boolean = false): Fluid = {

    val ownFluid = if (FluidRegistry.isFluidRegistered(id)) {
      HamCraftMod.logInfo("Fluid %s already registered, using existing (%s)", id, FluidRegistry.getFluid(id))
      false
    } else {
      HamCraftMod.logInfo("Registering fluid %s", id)
      val newFluid = new Fluid(id)
      newFluid.setUnlocalizedName((HamCraftMod.modId + "." + id).toLowerCase)
      newFluid.setLuminosity(luminosity)
      newFluid.setDensity(density)
      newFluid.setTemperature(temperature)
      newFluid.setViscosity(viscosity)
      newFluid.setGaseous(isGaseous)
      FluidRegistry.registerFluid(newFluid)
      true
    }
    val fluid = FluidRegistry.getFluid(id.toLowerCase)
    if (fluid.getBlock == null) {
      val block = new BlockFluid(fluid, ownFluid)
      GameRegistry.registerBlock(block, "fluid." + id)
      block.setCreativeTab(HamcraftCreativeTabs.main)
      fluid.setBlock(block)
    }
    if (FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000), emptyBucket) == null) {
      val bucket = Items.regItem(new ItemFluidBucket(fluid), id + "Bucket")
      FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket), emptyBucket)
    }
    if (FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid, 1000), emptyCan) == null) {
      val can = Items.regItem(new ItemFluidCan(fluid), id + "Can")
      FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(can), emptyCan)
    }
    fluid
  }

  HamCraftMod.logInfo("Fluids loaded")
}
