package com.tpl.hamcraft.machines.breeder

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.{SlotValidating, BaseContainer}
import net.bdew.lib.data.base.ContainerDataSlots

class ContainerBreeder(val te: TileBreeder, player: EntityPlayer) extends BaseContainer(te) with ContainerDataSlots {
  lazy val dataSource = te

  // input
  addSlotToContainer(new SlotValidating(te, 0, 56, 19))
  addSlotToContainer(new SlotValidating(te, 1, 74, 19))

  // output
  addSlotToContainer(new SlotValidating(te, 2, 133, 19))
  addSlotToContainer(new SlotValidating(te, 3, 151, 19))
  addSlotToContainer(new SlotValidating(te, 4, 151, 54))

  bindPlayerInventory(player.inventory, 8, 84, 142)

  def canInteractWith(entityplayer: EntityPlayer): Boolean = {
    return te.isUseableByPlayer(entityplayer)
  }
}
