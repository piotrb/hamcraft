package com.tpl.hamcraft.machines

import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.{SlotValidating, BaseContainer}
import net.bdew.lib.data.base.ContainerDataSlots

abstract class ContainerBase[T <: IInventory](private val te: T, player: EntityPlayer) extends BaseContainer(te) with ContainerDataSlots {

  def canInteractWith(entityplayer: EntityPlayer): Boolean = te.isUseableByPlayer(entityplayer)

  def addSlotSet(startingSlot: Int, rows: Int, cols: Int, startX: Int, startY: Int) {
    var inventorySlot = startingSlot
    (0 to rows-1).foreach(row => {
      (0 to cols-1).foreach(col => {
        val x = startX + (col * 18)
        val y = startY + (row * 18)
        addSlotToContainer(new SlotValidating(te, inventorySlot, x, y))
        inventorySlot += 1
      })
    })
  }

}