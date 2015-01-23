package com.tpl.hamcraft.machines

import net.bdew.lib.data.base.ContainerDataSlots
import net.bdew.lib.gui.{BaseContainer, SlotValidating}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory

abstract class ContainerBase[T <: IInventory](private val te: T, player: EntityPlayer) extends BaseContainer(te) with ContainerDataSlots {

  // bind player inventory from config
  //  if(te.isInstanceOf[TileConfigurable]) {
  //    val configTE = te.asInstanceOf[TileConfigurable]
  //    val info = configTE.config.getPlayerInventoryContainerInfo("player_inventory")
  //    bindPlayerInventory(player.inventory, info.x, info.y, info.hotbarY)
  //  }

  def addSlotSet(startingSlot: Int, rows: Int, cols: Int, startX: Int, startY: Int) {
    var inventorySlot = startingSlot
    (0 to rows - 1).foreach(row => {
      (0 to cols - 1).foreach(col => {
        val x = startX + (col * 18)
        val y = startY + (row * 18)
        addSlotToContainer(new SlotValidating(te, inventorySlot, x, y))
        inventorySlot += 1
      })
    })
  }

}
