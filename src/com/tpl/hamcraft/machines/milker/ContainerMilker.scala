package com.tpl.hamcraft.machines.milker

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.ContainerBase

class ContainerMilker(val te: TileMilker, player: EntityPlayer) extends ContainerBase[TileMilker](te, player) {
  lazy val dataSource = te

  // input
//  addSlotSet(startingSlot = te.inputSlot, rows = 1, cols = 1, startX = 62, startY = 18)

  // output
//  addSlotSet(startingSlot = te.outputSlots.head, rows = 1, cols = 1, startX = 152, startY = 18)

//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerInSlot, 53, 50))
//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerOutSlot, 74, 50))
}
