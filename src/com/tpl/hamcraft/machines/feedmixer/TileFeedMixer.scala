package com.tpl.hamcraft.machines.feedmixer

import com.tpl.hamcraft.power.TilePowered
import com.tpl.hamcraft.config.{Fluids, Machines}
import net.bdew.lib.data.DataSlotTank
import net.minecraftforge.fluids._
import net.minecraft.item.ItemStack
import net.bdew.lib.rotate.RotateableTile
import cpw.mods.fml.common.FMLLog
import com.tpl.hamcraft.machines.{TileFluidInput, TileFluidProcessor, TileIngredientSearch}

class TileFeedMixer extends TileFluidProcessor with TileFluidInput with TilePowered with RotateableTile with TileIngredientSearch
{
  lazy val cfg = Machines.feedmixer

  val inputTankSize = cfg.tankSize
  val inputTankFluidId = getMilkFluid.getID

  val outputTank = DataSlotTank("outputTank", this, cfg.tankSize)
  val outputTankFluidId = Fluids.babyfood.getID

  val milkPerRun = 250

  val ingredientSlots = 0 to 17

  val fluidInputContainerInSlot = 18
  val fluidInputContainerOutSlot = 19

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

  override def isItemValidForSlot(slot: Int, itemStack: ItemStack): Boolean = {
    slot match {
      case `fluidInputContainerInSlot` => isValidInputFluidContainer(itemStack)
      case `fluidInputContainerOutSlot` => false
      case x if ingredientSlots.contains(x) => true
      case _ => false
    }
  }
  // ---- Machine Operation

  def consumeIngredients: Boolean = {
    val wheatSlot = findIngredientSlot(ingredientSlots, "wheat", 1)
    val seedSlot = findIngredientSlot(ingredientSlots,"seedAny", 1)
    val mushroomSlot = findIngredientSlot(ingredientSlots,"mushroomAny", 1)
    if(inputTankAmount >= milkPerRun && wheatSlot.isDefined && seedSlot.isDefined && mushroomSlot.isDefined) {
      drainInputTank(milkPerRun)
      decrStackSize(wheatSlot.get, 1)
      decrStackSize(seedSlot.get, 1)
      decrStackSize(mushroomSlot.get, 1)
      return true
    }
    false
  }

  def tryStart(): Boolean = {
    if(consumeIngredients) {
      output.fill(1000, doFill = true)
      return true
    }
    false
  }

  // ---- End Machine Operation

}
