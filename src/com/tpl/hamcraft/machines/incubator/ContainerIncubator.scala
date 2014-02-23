package com.tpl.hamcraft.machines.incubator

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.machines.ContainerBase

class ContainerIncubator(val te: TileIncubator, player: EntityPlayer) extends ContainerBase[TileIncubator](te, player) {
  lazy val dataSource = te

  bindPlayerInventory(player.inventory, 8, 102, 160)
}
