package com.tpl.hamcraft.machines.feedmixer

import net.bdew.lib.power.{TileBaseProcessor, TileItemProcessor}
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.tile.ExposeTank
import com.tpl.hamcraft.config.{Fluids, Items, Machines}
import net.bdew.lib.data.{DataSlotItemStack, DataSlotTankRestricted}
import net.minecraftforge.fluids._
import net.minecraftforge.common.ForgeDirection
import net.minecraft.item.{Item, ItemBucketMilk, ItemStack}
import com.tpl.hamcraft.items.EmancipatedAnimal
import net.bdew.lib.rotate.RotateableTile
import net.bdew.lib.data.base.UpdateKind
import net.bdew.lib.items.ItemUtils
import cpw.mods.fml.common.FMLLog
import net.bdew.lib.data.DataSlotTankRestricted
import net.minecraftforge.oredict.OreDictionary
import com.tpl.hamcraft.machines.TileIngredientSearch

class TileFeedMixer extends TileBaseProcessor with TilePowered with ExposeTank with RotateableTile with TileIngredientSearch
{
  lazy val cfg = Machines.feedmixer

  val milkTank = DataSlotTankRestricted("milkTank", this, cfg.tankSize, getMilkFluid.getID)
  val feedTank = DataSlotTankRestricted("feedTank", this, cfg.tankSize, Fluids.babyfood.getID)

  val output = DataSlotTankRestricted("outputTank", this, 1000, Fluids.babyfood.getID)

  val milkPerRun = 250

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
      case x if ingredientSlots.contains(x) => true
      case _ => false
    }
  }

  def isValidMilkContainer(itemStack: ItemStack): Boolean = {
    val containerFluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack)
    containerFluidStack.getFluid == getMilkFluid
  }

  // ---- Machine Operation

//  def isWorking = false

  def isWorking = output.getFluidAmount > 0

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

  def consumeIngredients: Boolean = {
    val wheatSlot = findIngredientSlot(ingredientSlots, "wheat", 1)
    val seedSlot = findIngredientSlot(ingredientSlots,"seedAny", 1)
    val mushroomSlot = findIngredientSlot(ingredientSlots,"mushroomAny", 1)
    if(milkTank.getFluidAmount >= milkPerRun && wheatSlot.isDefined && seedSlot.isDefined && mushroomSlot.isDefined) {
      milkTank.drain(milkPerRun, true)
      decrStackSize(wheatSlot.get, 1)
      decrStackSize(seedSlot.get, 1)
      decrStackSize(mushroomSlot.get, 1)
      return true
    }
    false
  }

  def tryStart(): Boolean = {
    if(consumeIngredients) {
      output.fill(1000, true)
      return true
    }
    false
  }

  def tryFinish(): Boolean = {
    if(feedTank.getCapacity - feedTank.getFluidAmount >= 1000) {
      val outputStack = output.drain(1000, true)
      feedTank.fill(outputStack, true)
      return true
    }
    false
  }

  // ---- End Machine Operation

  override def canExtractItem(slot: Int, item: ItemStack, side: Int) = false

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = true
  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = feedTank.drain(1000, doDrain)
  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = feedTank.drain(maxDrain, doDrain)

}
