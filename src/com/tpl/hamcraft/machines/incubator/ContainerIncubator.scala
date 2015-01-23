//package com.tpl.hamcraft.machines.incubator
//
//import net.minecraft.entity.player.EntityPlayer
//import com.tpl.hamcraft.machines.ContainerBase
//import net.bdew.lib.gui.SlotValidating
//
//class ContainerIncubator(val te: TileIncubator, player: EntityPlayer) extends ContainerBase[TileIncubator](te, player) {
//  lazy val dataSource = te
//
//  // input
//  addSlotSet(startingSlot = te.inputSlot, rows = 1, cols = 1, startX = 62, startY = 18)
//
//  // output
//  addSlotSet(startingSlot = te.outputSlots.head, rows = 1, cols = 1, startX = 152, startY = 18)
//
//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerInSlot, 53, 50))
//  addSlotToContainer(new SlotValidating(te, te.fluidInputContainerOutSlot, 74, 50))
//
//  bindPlayerInventory(player.inventory, 8, 84, 142)
//}
