package com.tpl.hamcraft.machines.breeder

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.SlotValidating
import com.tpl.hamcraft.machines.ContainerBase

class ContainerBreeder(val te: TileBreeder, player: EntityPlayer) extends ContainerBase[TileBreeder](te, player) {
  lazy val dataSource = te

  // input
  addSlotSet(startingSlot = 0, rows = 2, cols = 1, startX = 46, startY = 19)

  // output
  addSlotSet(startingSlot = 2, rows = 2, cols = 1, startX = 152, startY = 19)

  // child
  addSlotToContainer(new SlotValidating(te, 4, 98, 61))

  // feed
  addSlotSet(startingSlot = 5, rows = 1, cols = 9, startX = 8, startY = 81)


  bindPlayerInventory(player.inventory, 8, 102, 160)


}
