package com.tpl.hamcraft.machines

import net.bdew.lib.power.TileBaseProcessor
import net.bdew.lib.data.{DataSlotTank, DataSlotTankRestricted}
import net.bdew.lib.data.base.UpdateKind
import net.minecraft.item.ItemStack
import net.minecraftforge.common.ForgeDirection
import net.minecraftforge.fluids.{FluidRegistry, FluidStack, Fluid}
import net.bdew.lib.tile.ExposeTank
import cpw.mods.fml.common.FMLLog

abstract class TileFluidProcessor extends TileBaseProcessor with ExposeTank
{
  val outputTankSize: Int = 1000
  val outputTankFluid: String
  lazy val output: DataSlotTankRestricted = DataSlotTankRestricted("output", this, outputTankSize, outputTankFluidId).setUpdate(UpdateKind.SAVE)

  def outputTankFluidId: Int = {
    val fluid = FluidRegistry.getFluid(outputTankFluid)
    if (fluid == null) {
      FMLLog.severe("Could not find %s fluid!", outputTankFluid)
      throw new Exception(String.format("Could not find %s fluid!", outputTankFluid))
    }
    fluid.getID
  }

  val outputTank: DataSlotTank

  def isWorking = output.getFluidAmount > 0

  def tryFinish(): Boolean = {
    val outputStack = output.drain(1000, true)
    outputTank.fill(outputStack, true)
    output.getFluidAmount == 0
  }

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = false

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = true
  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = outputTank.drain(1000, doDrain)
  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = outputTank.drain(maxDrain, doDrain)
}