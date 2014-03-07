package com.tpl.hamcraft.machines.milker

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.{TileConfigurable, ContainerBase}

class ContainerMilker(val te: TileMilker, player: EntityPlayer) extends ContainerBase[TileMilker](te, player) {
  lazy val dataSource = te

//  if(te.isInstanceOf[TileConfigurable]) {
//    val configTE = te.asInstanceOf[TileConfigurable]
  val configTE = te
    val info = configTE.config.getPlayerInventoryContainerInfo("player_inventory")
    bindPlayerInventory(player.inventory, info.x, info.y, info.hotbarY)
//  }

  // input
//  addSlotSet(startingSlot = te.inputSlot, rows = 1, cols = 1, startX = 62, startY = 18)

  // output
//  addSlotSet(startingSlot = te.outputSlots.head, rows = 1, cols = 1, startX = 152, startY = 18)

//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerInSlot, 53, 50))
//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerOutSlot, 74, 50))
}
