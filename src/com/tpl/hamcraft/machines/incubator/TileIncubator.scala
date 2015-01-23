//package com.tpl.hamcraft.machines.incubator
//
//import net.bdew.lib.power.TileItemProcessor
//import com.tpl.hamcraft.config.Machines
//import com.tpl.hamcraft.power.TilePowered
//import net.bdew.lib.rotate.RotateableTile
//import com.tpl.hamcraft.machines.{TileFluidInput, TileIngredientSearch}
//import net.minecraft.item.ItemStack
//import com.tpl.hamcraft.items.EmancipatedAnimal
//
//class TileIncubator extends TileItemProcessor with TileFluidInput with TilePowered with RotateableTile with TileIngredientSearch {
//  lazy val cfg = Machines.incubator
//
//  val inputSlot = 0
//  val fluidInputContainerInSlot = 1
//  val fluidInputContainerOutSlot = 2
//  val outputSlots = 3 to 3
//
//  val inputTankSize = cfg.tankSize
//  val inputTankFluid = "babyfood"
//
//  val feedPerCycle = cfg.feedPerCycle
//
//  def getSizeInventory = 4
//
//  allowSided = true
//
//  private def itemIsEmancipatedAnimalChild(item: ItemStack): Boolean = {
//    if(item == null) return false
//    item.getItem match {
//      case _:EmancipatedAnimal =>
//        EmancipatedAnimal.getAnimalType(item) != "invalid" && EmancipatedAnimal.isChild(item)
//      case _ =>
//        false
//    }
//  }
//
//  override def isItemValidForSlot(slot: Int, itemstack: ItemStack): Boolean = {
//    slot match {
//      case `inputSlot` => itemIsEmancipatedAnimalChild(itemstack)
//      case `fluidInputContainerInSlot` => isValidInputFluidContainer(itemstack)
//      case `fluidInputContainerOutSlot` => false
//      case x if outputSlots.contains(x) => false
//      case _ => false
//    }
//  }
//
//  private def inputIsChild = itemIsEmancipatedAnimalChild(getStackInSlot(inputSlot))
//  private def hasFeed = inputTankAmount > feedPerCycle
//
//  private def healChild(itemStack: ItemStack) {
//    if(itemStack.getItemDamage > 0) {
//      itemStack.setItemDamage(itemStack.getItemDamage - 1)
//      if(itemStack.getItemDamage == 0) {
//        EmancipatedAnimal.growUp(itemStack)
//      }
//    }
//  }
//
//  override def onInventoryChanged() {
//    if(!(output :== null) && getStackInSlot(inputSlot) == null) {
//      output := null
//      progress := 0
//    }
//    super.onInventoryChanged()
//  }
//
//  def tryStart(): Boolean = {
//    if(inputIsChild && hasFeed) {
//      val child = getStackInSlot(inputSlot).copy()
//      healChild(child)
//      output := child
//      return true
//    }
//    false
//  }
//
//  override def tryFinish(): Boolean = {
//
//    if(EmancipatedAnimal.isChild(output)) {
//      setInventorySlotContents(inputSlot, output)
//      output := null
//    } else {
//      if(getStackInSlot(outputSlots.head) != null) return false // wait for slot to empty
//      setInventorySlotContents(outputSlots.head, output)
//      setInventorySlotContents(inputSlot, null)
//      output := null
//    }
//    drainInputTank(feedPerCycle)
//    output :== null
//  }
//
//  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = outputSlots.contains(slot)
//
//}
