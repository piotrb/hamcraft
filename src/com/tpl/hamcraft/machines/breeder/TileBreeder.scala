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
import net.bdew.lib.rotate.RotateableTile
import com.tpl.hamcraft.machines.TileIngredientSearch

class TileBreeder extends TileItemProcessor with TilePowered with ExposeTank with RotateableTile with TileIngredientSearch {
  lazy val cfg = Machines.breeder

  val slotsAnimals = 0 to 1
  val transferSlots = 2 to 3
  val outputSlots = 4 to 4
  val feedSlots = 5 to 14

  val tank = DataSlotTankRestricted("tank", this, cfg.tankSize, FluidRegistry.WATER.getID)

  def getSizeInventory = 15

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
    slot match {
      case x if slotsAnimals.contains(x) => {
        if (itemstack != null && itemstack.getItem != null) {
          getStackInSlot(slot) == null &&
            itemstack.stackSize == 1 &&
            itemIsEmancipatedAnimal(itemstack)
        } else false
      }
      case x if transferSlots.contains(x) => false
      case x if outputSlots.contains(x) => false
      case x if feedSlots.contains(x) => true
      case _ => false
    }
  }

  def isReadyToStart: Boolean = {
    val parent1 = getStackInSlot(0)
    val parent2 = getStackInSlot(1)
    if(parent1 != null && parent2 != null && tank.getFluidAmount >= 1000) {
      if(Items.emancipatedAnimal.getAnimalType(parent1) == Items.emancipatedAnimal.getAnimalType(parent2)) {

        val info = EmancipatedAnimal.infoForStack(parent1).get
        val slot = findIngredientSlot(feedSlots, info.breedingFood, 2)

        return slot.isDefined
      }
    }
    false
  }

  def tryStart(): Boolean = {
    if(isReadyToStart) {
      val parent1 = getStackInSlot(0)
      val parent2 = getStackInSlot(1)

      val info = EmancipatedAnimal.infoForStack(parent1).get
      val slot = findIngredientSlot(feedSlots, info.breedingFood, 2).get

      output := EmancipatedAnimal.makeChild(parent1, parent2)
      decrStackSize(slot, 2)
      if(parent1.stackSize <= 0) setInventorySlotContents(0, null)
      if(parent2.stackSize <= 0) setInventorySlotContents(1, null)
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

        if(parent1 != null && parent1.stackSize > 0) setInventorySlotContents(2, parent1)
        if(parent2 != null && parent2.stackSize > 0) setInventorySlotContents(3, parent2)

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
