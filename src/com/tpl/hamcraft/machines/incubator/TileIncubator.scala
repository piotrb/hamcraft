package com.tpl.hamcraft.machines.incubator

import net.bdew.lib.power.TileItemProcessor
import com.tpl.hamcraft.config.{Fluids, Machines}
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.rotate.RotateableTile
import com.tpl.hamcraft.machines.{TileFluidInput, TileIngredientSearch}
import net.minecraft.item.ItemStack

class TileIncubator extends TileItemProcessor with TileFluidInput with TilePowered with RotateableTile with TileIngredientSearch {
  lazy val cfg = Machines.incubator

  val fluidInputContainerInSlot = 0
  val fluidInputContainerOutSlot = 1
  val outputSlots = 2 to 2

  val inputTankSize = cfg.tankSize
  val inputTankFluidId = Fluids.babyfood.getID

  def getSizeInventory = 3

  allowSided = true

//  override def isItemValidForSlot(slot: Int, itemstack: ItemStack): Boolean = {
//    slot match {
//      case x if slotsAnimals.contains(x) => {
//        if (itemstack != null && itemstack.getItem != null) {
//          getStackInSlot(slot) == null &&
//            itemstack.stackSize == 1 &&
//            itemIsEmancipatedAnimal(itemstack)
//        } else false
//      }
//      case x if transferSlots.contains(x) => false
//      case x if outputSlots.contains(x) => false
//      case x if feedSlots.contains(x) => true
//      case _ => false
//    }
//  }

  def tryStart(): Boolean = {
//    if(isReadyToStart) {
//      val parent1 = getStackInSlot(0)
//      val parent2 = getStackInSlot(1)
//
//      val info = EmancipatedAnimal.infoForStack(parent1).get
//      val slot = findIngredientSlot(feedSlots, info.breedingFood, 2).get
//
//      output := EmancipatedAnimal.makeChild(parent1, parent2)
//      decrStackSize(slot, 2)
//      if(parent1.stackSize <= 0) setInventorySlotContents(0, null)
//      if(parent2.stackSize <= 0) setInventorySlotContents(1, null)
//      return true
//    }
    false
  }

  override def tryFinish(): Boolean = {
//    if(getStackInSlot(2) == null && getStackInSlot(3) == null && getStackInSlot(4) == null) {
//      if(super.tryFinish()) {
//        val parent1 = getStackInSlot(0)
//        val parent2 = getStackInSlot(1)
//        setInventorySlotContents(0, null)
//        setInventorySlotContents(1, null)
//
//        if(parent1 != null && parent1.stackSize > 0) setInventorySlotContents(2, parent1)
//        if(parent2 != null && parent2.stackSize > 0) setInventorySlotContents(3, parent2)
//
//        tank.drain(1000, true)
//        return true
//      }
//    }
    false
  }

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = outputSlots.contains(slot)

}
