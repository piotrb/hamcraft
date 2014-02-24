package com.tpl.hamcraft.machines

import net.bdew.lib.data.base.TileDataSlots
import net.bdew.lib.tile.ExposeTank
import net.bdew.lib.data.DataSlotTankRestricted
import net.minecraftforge.common.ForgeDirection
import net.minecraftforge.fluids.{FluidRegistry, FluidContainerRegistry, IFluidTank}
import net.bdew.lib.tile.inventory.BaseInventory
import net.minecraft.item.ItemStack
import cpw.mods.fml.common.FMLLog


trait TileFluidInput extends TileDataSlots with ExposeTank with BaseInventory
{
  val inputTankSize: Int
  val inputTankFluid: String
  val fluidInputContainerInSlot: Int  // set to -1 to disable
  val fluidInputContainerOutSlot: Int // set to -1 to disable

  lazy val inputTank = DataSlotTankRestricted("inputTank", this, inputTankSize, inputTankFluidId)

  def inputTankAmount = inputTank.getFluidAmount

  private def inputTankFluidId: Int = {
    val fluid = FluidRegistry.getFluid(inputTankFluid)
    if (fluid == null) {
      FMLLog.severe("Could not find %s fluid!", inputTankFluid)
      throw new Exception(String.format("Could not find %s fluid!", inputTankFluid))
    }
    fluid.getID
  }

  def drainInputTank(amount: Int) {
    inputTank.drain(amount, true)
  }

  def isValidInputFluidContainer(itemStack: ItemStack): Boolean = {
    val containerFluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack)
    if(containerFluidStack != null) {
      containerFluidStack.fluidID == inputTank.fluidID
    } else {
      false
    }
  }

  private def containerOutputCanAdd(itemStack: ItemStack): Boolean = {
    val containerOutputStack = getStackInSlot(fluidInputContainerOutSlot)
    if(containerOutputStack == null) return true
    if(containerOutputStack.stackSize == 0) return true
    if(containerOutputStack.getItem == itemStack.getItem) {
      if(containerOutputStack.stackSize < containerOutputStack.getMaxStackSize) return true
    }
    false
  }

  private def handleFluidInput() {
    if(fluidInputContainerInSlot < 0 || fluidInputContainerOutSlot < 0) return

    val itemStack = getStackInSlot(fluidInputContainerInSlot)
    if (itemStack != null) {
      if (isValidInputFluidContainer(itemStack)) {
        val containerData = FluidContainerRegistry.getRegisteredFluidContainerData.find(cd => cd.filledContainer.getItem == itemStack.getItem).get
        val fluidStack = containerData.fluid
        if(inputTank.fill(fluidStack, doFill = false) == fluidStack.amount) {
          val containerOutputStack = getStackInSlot(fluidInputContainerOutSlot)
          if (containerOutputCanAdd(containerData.emptyContainer)) {
            inputTank.fill(fluidStack, doFill = true)
            decrStackSize(fluidInputContainerInSlot, 1)
            if (containerOutputStack != null && containerOutputStack.stackSize > 0) {
              containerOutputStack.stackSize += 1
            } else {
              setInventorySlotContents(fluidInputContainerOutSlot, containerData.emptyContainer.copy())
            }
          }
        }
      }
    }
  }

  override def tickServer() {
    handleFluidInput()
    super.tickServer()
  }

  def getTankFromDirection(dir: ForgeDirection): IFluidTank = inputTank
}
