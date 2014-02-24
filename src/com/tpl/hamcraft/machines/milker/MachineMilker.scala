package com.tpl.hamcraft.machines.milker

import net.bdew.lib.machine.{ProcessorMachine, Machine}
import net.bdew.lib.gui.GuiProvider
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container

class MachineMilker extends Machine("Milker", new BlockMilker(_)) with GuiProvider with ProcessorMachine {
  def guiId = 4
  type TEClass = TileMilker

  lazy val tankSize = tuning.getInt("TankSize")

  @SideOnly(Side.CLIENT)
  def getGui(te: TileMilker, player: EntityPlayer): GuiContainer = new GuiMilker(te, player)
  def getContainer(te: TileMilker, player: EntityPlayer): Container = new ContainerMilker(te, player)
}
