package com.tpl.hamcraft.machines.incubator

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.ContainerBase

class ContainerIncubator(val te: TileIncubator, player: EntityPlayer) extends ContainerBase[TileIncubator](te, player) {
  lazy val dataSource = te

  // input
  addSlotSet(startingSlot = 0, rows = 1, cols = 1, startX = 71, startY = 36)

  // output
  addSlotSet(startingSlot = 1, rows = 3, cols = 3, startX = 116, startY = 18)


  bindPlayerInventory(player.inventory, 8, 84, 142)
}
