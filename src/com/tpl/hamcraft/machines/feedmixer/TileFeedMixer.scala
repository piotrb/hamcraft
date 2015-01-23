package com.tpl.hamcraft.machines.feedmixer

import com.tpl.hamcraft.machines.{TileFluidInput, TileFluidProcessor, TileIngredientSearch}
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.data.DataSlotTank
import net.bdew.lib.rotate.RotatableTile

class TileFeedMixer extends TileFluidProcessor with TileFluidInput with TilePowered with RotatableTile with TileIngredientSearch {
  lazy val cfg = MachineFeedMixer

    val inputTankSize = cfg.tankSize
    val inputTankFluid = "milk"

    val outputTank = DataSlotTank("outputTank", this, cfg.tankSize)
    val outputTankFluid = "babyfood"

    val milkPerRun = cfg.milkPerRun
    val feedPerRun = cfg.feedPerRun

    val ingredientSlots = 0 to 17

    val fluidInputContainerInSlot = 18
    val fluidInputContainerOutSlot = 19

    def getSizeInventory = 20

    allowSided = true

  //  private def getMilkFluid: Fluid = {
  //    val milk = FluidRegistry.getFluid("milk")
  //    if (milk == null) {
  //      FMLLog.severe("Could not find milk fluid!")
  //      throw new Exception("Could not find milk fluid!")
  //    }
  //    milk
  //  }
  //
  //  override def isItemValidForSlot(slot: Int, itemStack: ItemStack): Boolean = {
  //    slot match {
  //      case `fluidInputContainerInSlot` => isValidInputFluidContainer(itemStack)
  //      case `fluidInputContainerOutSlot` => false
  //      case x if ingredientSlots.contains(x) => true
  //      case _ => false
  //    }
  //  }
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
        output.fill(feedPerRun, doFill = true)
        return true
      }
      false
    }

    // ---- End Machine Operation

}
