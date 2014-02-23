package com.tpl.hamcraft.machines

import net.minecraftforge.oredict.OreDictionary
import scala.collection.JavaConversions._
import net.bdew.lib.tile.inventory.BaseInventory

trait TileIngredientSearch extends BaseInventory {

  def findIngredientSlot(slots: Range, name: String, amount: Int): Option[Int] = {
    val ores = OreDictionary.getOres(name)
    slots.find(slot => {
      val stack = getStackInSlot(slot)
      stack != null && ores.exists(ore => {
        ore.getItem == stack.getItem && stack.stackSize >= amount
      })
    })
  }

}
