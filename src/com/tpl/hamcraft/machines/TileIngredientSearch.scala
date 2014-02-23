package com.tpl.hamcraft.machines

import net.minecraftforge.oredict.OreDictionary
import net.bdew.lib.power.TileItemProcessor
import com.tpl.hamcraft.power.TilePowered
import net.bdew.lib.tile.ExposeTank
import net.bdew.lib.rotate.RotateableTile
import scala.collection.JavaConversions._
import net.minecraft.item.ItemStack
import net.bdew.lib.tile.inventory.BaseInventory

trait TileIngredientSearch extends BaseInventory {

  def findIngredientSlot(slots: Range, name: String, amount: Int): Option[Int] = {
    val ores = OreDictionary.getOres(name)//.toArray
    slots.find(slot => {
      val stack = getStackInSlot(slot)
      stack != null && ores.exists(ore => {
        ore.getItem == stack.getItem && stack.stackSize >= amount
      })
    })
  }

}
