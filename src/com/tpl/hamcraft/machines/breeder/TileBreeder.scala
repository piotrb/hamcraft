package com.tpl.hamcraft.machines.breeder

import net.bdew.lib.data.base.UpdateKind
import net.bdew.lib.tile.inventory.PersistentInventoryTile
import net.bdew.lib.power.{TileItemProcessor, DataSlotPower}
import net.bdew.lib.data.{DataSlotItemStack, DataSlotTankRestricted, DataSlotString}
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.common.ForgeDirection
import com.tpl.hamcraft.config.{Items, Machines}
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.tile.ExposeTank
import net.minecraftforge.fluids.{FluidStack, Fluid, IFluidTank, FluidRegistry}
import com.tpl.hamcraft.items.EmancipatedAnimal
import net.bdew.lib.items.ItemUtils

class TileBreeder extends TileItemProcessor with TilePowered with ExposeTank {
  lazy val cfg = Machines.breeder

  val slotsAnimals = 0 to 1
  val transferSlots = 2 to 3
  val outputSlots = 4 to 4

  val tank = DataSlotTankRestricted("tank", this, cfg.tankSize, FluidRegistry.WATER.getID)

  def getSizeInventory = 5

  allowSided = true

  def getTankFromDirection(dir: ForgeDirection): IFluidTank = tank

  def itemIsEmancipatedAnimal(item: ItemStack): Boolean = {
    item.getItem match {
      case _:EmancipatedAnimal =>
        Items.emancipatedAnimal.getAnimalType(item) != "invalid" && !Items.emancipatedAnimal.isChild(item)
      case _ => false
    }
  }

  override def isItemValidForSlot(slot: Int, itemstack: ItemStack): Boolean = {
    if(getStackInSlot(slot) != null) return false
    if (itemstack == null || itemstack.getItem == null) return false
    if(itemstack.stackSize > 1) return false

    slot match {
      case 0 => itemIsEmancipatedAnimal(itemstack)
      case 1 => itemIsEmancipatedAnimal(itemstack)
      case _ => false
    }
  }

  def isReadyToStart: Boolean = {
    val parent1 = getStackInSlot(0)
    val parent2 = getStackInSlot(1)
    if(parent1 != null && parent2 != null && tank.getFluidAmount >= 1000) {
      if(Items.emancipatedAnimal.getAnimalType(parent1) == Items.emancipatedAnimal.getAnimalType(parent2)) {
        return true
      }
    }
    false
  }

  def tryStart(): Boolean = {
    if(isReadyToStart) {
      val parent1 = getStackInSlot(0)
      val parent2 = getStackInSlot(1)
      output := EmancipatedAnimal.makeChild(parent1, parent2)
      return true
    }
    false
  }

  override def tryFinish(): Boolean = {
    if(getStackInSlot(2) == null && getStackInSlot(3) == null && getStackInSlot(4) == null) {
      if(super.tryFinish()) {
        val parent1 = getStackInSlot(0)
        val parent2 = getStackInSlot(1)
        setInventorySlotContents(0, null)
        setInventorySlotContents(1, null)

        if(parent1.stackSize > 0) setInventorySlotContents(2, parent1)
        if(parent2.stackSize > 0) setInventorySlotContents(3, parent2)

        tank.drain(1000, true)
        return true
      }
    }
    false
  }

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = outputSlots.contains(slot)

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = false
  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = null
  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = null

}
