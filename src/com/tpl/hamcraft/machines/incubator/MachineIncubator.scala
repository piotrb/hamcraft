package com.tpl.hamcraft.machines.incubator

import net.bdew.lib.machine.{ProcessorMachine, Machine}
import net.bdew.lib.gui.GuiProvider
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container

class MachineIncubator extends Machine("Incubator", new BlockIncubator(_)) with GuiProvider with ProcessorMachine {
  def guiId = 3
  type TEClass = TileIncubator

  lazy val tankSize = tuning.getInt("TankSize")
  lazy val feedPerCycle = tuning.getInt("feedPerCycle")

  @SideOnly(Side.CLIENT)
  def getGui(te: TileIncubator, player: EntityPlayer): GuiContainer = new GuiIncubator(te, player)
  def getContainer(te: TileIncubator, player: EntityPlayer): Container = new ContainerIncubator(te, player)
}
