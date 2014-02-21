package com.tpl.hamcraft.machines.feedmixer

import net.bdew.lib.power.{TileBaseProcessor, TileItemProcessor}
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.tile.ExposeTank
import com.tpl.hamcraft.config.{Fluids, Items, Machines}
import net.bdew.lib.data.{DataSlotItemStack, DataSlotTankRestricted}
import net.minecraftforge.fluids._
import net.minecraftforge.common.ForgeDirection
import net.minecraft.item.{ItemBucketMilk, ItemStack}
import com.tpl.hamcraft.items.EmancipatedAnimal
import net.bdew.lib.rotate.RotateableTile
import net.bdew.lib.data.base.UpdateKind
import net.bdew.lib.items.ItemUtils
import cpw.mods.fml.common.FMLLog
import net.bdew.lib.data.DataSlotTankRestricted

class TileFeedMixer extends TileBaseProcessor with TilePowered with ExposeTank with RotateableTile
{
  lazy val cfg = Machines.feedmixer

  val milkTank = DataSlotTankRestricted("milkTank", this, cfg.tankSize, getMilkFluid.getID)
  val feedTank = DataSlotTankRestricted("feedTank", this, cfg.tankSize, Fluids.babyfood.getID)

  val ingredientSlots = (0 to 17)
  val milkInputSlot = 18
  val containerOutputSlot = 19

  def getSizeInventory = 20

  allowSided = true

  private def getMilkFluid: Fluid = {
    val milk = FluidRegistry.getFluid("milk")
    if (milk == null) {
      FMLLog.severe("Could not find milk fluid!")
      throw new Exception("Could not find milk fluid!")
    }
    milk
  }

  def getTankFromDirection(dir: ForgeDirection): IFluidTank = milkTank

  override def isItemValidForSlot(slot: Int, itemStack: ItemStack): Boolean = {
    slot match {
      case `containerOutputSlot` => false
      case `milkInputSlot` => isValidMilkContainer(itemStack)
//      case `ingredientSlots` => true
      case x if ingredientSlots.contains(x) => true
      case _ => false
    }
  }

  def isValidMilkContainer(itemStack: ItemStack): Boolean = {
    val containerFluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack)
    containerFluidStack.getFluid == getMilkFluid
//    FluidContainerRegistry.containsFluid(itemStack, new FluidStack(getMilkFluid, containerFluidStack.amount))
//    FMLLog.info("Fluid Stack: %s (%d)", fluidStack.getFluid.getLocalizedName.asInstanceOf[AnyRef], fluidStack.amount.asInstanceOf[AnyRef])
//    false
  }

//  def itemIsEmancipatedAnimal(item: ItemStack): Boolean = {
//    item.getItem match {
//      case _:EmancipatedAnimal =>
//        Items.emancipatedAnimal.getAnimalType(item) != "invalid" && !Items.emancipatedAnimal.isChild(item)
//      case _ => false
//    }
//  }
//
//  override def isItemValidForSlot(slot: Int, itemstack: ItemStack): Boolean = {
//    if(getStackInSlot(slot) != null) return false
//    if (itemstack == null || itemstack.getItem == null) return false
//    if(itemstack.stackSize > 1) return false
//
//    slot match {
//      case 0 => itemIsEmancipatedAnimal(itemstack)
//      case 1 => itemIsEmancipatedAnimal(itemstack)
//      case _ => false
//    }
//  }
//
//  def isReadyToStart: Boolean = {
//    val parent1 = getStackInSlot(0)
//    val parent2 = getStackInSlot(1)
//    if(parent1 != null && parent2 != null && tank.getFluidAmount >= 1000) {
//      if(Items.emancipatedAnimal.getAnimalType(parent1) == Items.emancipatedAnimal.getAnimalType(parent2)) {
//        return true
//      }
//    }
//    false
//  }
//

  // ---- Machine Operation

//  def isWorking = false

  var isWorking = false

  def containerOutputCanAdd(itemStack: ItemStack): Boolean = {
    val containerOutputStack = getStackInSlot(containerOutputSlot)
    if(containerOutputStack == null) return true
    if(containerOutputStack.stackSize == 0) return true
    if(containerOutputStack.getItem == itemStack.getItem) {
      if(containerOutputStack.stackSize < containerOutputStack.getMaxStackSize) return true
    }
    false
  }

  def handleMilkInput() {
    val milkItemStack = getStackInSlot(milkInputSlot)
    if (milkItemStack != null) {
      if (isValidMilkContainer(milkItemStack)) {
        val containerData = FluidContainerRegistry.getRegisteredFluidContainerData.find(cd => cd.filledContainer.getItem == milkItemStack.getItem).get
        val fluidStack = containerData.fluid
        if(milkTank.fill(fluidStack, false) == fluidStack.amount) {
          val containerOutputStack = getStackInSlot(containerOutputSlot)
          if (containerOutputCanAdd(containerData.emptyContainer)) {
            milkTank.fill(fluidStack, true)
            decrStackSize(milkInputSlot, 1)
            if (containerOutputStack != null && containerOutputStack.stackSize > 0) {
              containerOutputStack.stackSize += 1
            } else {
              setInventorySlotContents(containerOutputSlot, containerData.emptyContainer.copy())
            }
          }
        }
      }
    }
  }

  override def tickServer() {
    handleMilkInput()
    super.tickServer()
  }

  def tryStart(): Boolean = {
    isWorking = true
    true
//    if(isReadyToStart) {
//      val parent1 = getStackInSlot(0)
//      val parent2 = getStackInSlot(1)
//      output := EmancipatedAnimal.makeChild(parent1, parent2)
//      if(parent1.stackSize <= 0) setInventorySlotContents(0, null)
//      if(parent2.stackSize <= 0) setInventorySlotContents(1, null)
//      return true
//    }
//    false
  }

  def tryFinish(): Boolean = {
    //    output := ItemUtils.addStackToSlots(output, this, outputSlots, false)
    //    return output :== null
    true
  }

  // ---- End Machine Operation

  //
//  override def tryFinish(): Boolean = {
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
//    false
//  }
//
//  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = outputSlots.contains(slot)
//
//  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = false
//  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = null
//  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = null

}
