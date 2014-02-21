package com.tpl.hamcraft.machines

import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.BaseContainer
import net.bdew.lib.data.base.ContainerDataSlots

abstract class ContainerBase[T <: IInventory](private val te: T, player: EntityPlayer) extends BaseContainer(te) with ContainerDataSlots {
  def canInteractWith(entityplayer: EntityPlayer): Boolean = {
    return te.isUseableByPlayer(entityplayer)
  }
}