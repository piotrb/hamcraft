package com.tpl.hamcraft.machines

import net.bdew.lib.power.TileBaseProcessor
import net.bdew.lib.data.{DataSlotTank, DataSlotTankRestricted}
import net.bdew.lib.data.base.UpdateKind
import net.minecraft.item.ItemStack
import net.minecraftforge.common.ForgeDirection
import net.minecraftforge.fluids.{FluidStack, Fluid}
import net.bdew.lib.tile.ExposeTank

abstract class TileFluidProcessor extends TileBaseProcessor with ExposeTank
{
  val outputTankSize: Int = 1000
  val outputTankFluidId: Int
  lazy val output: DataSlotTankRestricted = DataSlotTankRestricted("output", this, outputTankSize, outputTankFluidId).setUpdate(UpdateKind.SAVE)

  val outputTank: DataSlotTank

  def isWorking = output.getFluidAmount > 0

  def tryFinish(): Boolean = {
    val outputStack = output.drain(1000, true)
    outputTank.fill(outputStack, true)
    return output.getFluidAmount == 0
  }

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = false

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = true
  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = outputTank.drain(1000, doDrain)
  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = outputTank.drain(maxDrain, doDrain)
}