package com.tpl.hamcraft.machines.feedmixer

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.ContainerBase
import net.bdew.lib.gui.SlotValidating

class ContainerFeedMixer(val te: TileFeedMixer, player: EntityPlayer) extends ContainerBase[TileFeedMixer](te, player) {
  lazy val dataSource = te

  addInputSlots

  addSlotToContainer(new SlotValidating(te, 18, 40, 13))
  addSlotToContainer(new SlotValidating(te, 19, 60, 13))

  def addInputSlots {
    var inventorySlot = 0
    (0 to 1).foreach(row => {
      (0 to 8).foreach(col => {
        val x = 8 + (col * 18)
        val y = 43 + (row * 18)
        addSlotToContainer(new SlotValidating(te, inventorySlot, x, y))
        inventorySlot += 1
      })
    })
  }

  bindPlayerInventory(player.inventory, 8, 84, 142)

}
