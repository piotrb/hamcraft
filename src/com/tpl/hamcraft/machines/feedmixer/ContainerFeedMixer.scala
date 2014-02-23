package com.tpl.hamcraft.machines.feedmixer

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.ContainerBase
import net.bdew.lib.gui.SlotValidating

class ContainerFeedMixer(val te: TileFeedMixer, player: EntityPlayer) extends ContainerBase[TileFeedMixer](te, player) {
  lazy val dataSource = te

  addSlotSet(startingSlot = 0, rows = 2, cols = 9, startX = 8, startY = 43)

  addSlotToContainer(new SlotValidating(te, 18, 40, 13))
  addSlotToContainer(new SlotValidating(te, 19, 60, 13))

  bindPlayerInventory(player.inventory, 8, 84, 142)

}
