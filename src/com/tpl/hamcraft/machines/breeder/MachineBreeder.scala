//package com.tpl.hamcraft.machines.breeder
//
//import net.bdew.lib.machine.{ProcessorMachine, Machine}
//import net.bdew.lib.gui.GuiProvider
//import cpw.mods.fml.relauncher.{Side, SideOnly}
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.client.gui.inventory.GuiContainer
//import net.minecraft.inventory.Container
//
//class MachineBreeder extends Machine("Breeder", new BlockBreeder(_)) with GuiProvider with ProcessorMachine {
//  def guiId = 1
//  type TEClass = TileBreeder
//
//  lazy val tankSize = tuning.getInt("TankSize")
//  lazy val waterPerOperation = tuning.getInt("waterPerOperation")
//
//  @SideOnly(Side.CLIENT)
//  def getGui(te: TileBreeder, player: EntityPlayer): GuiContainer = new GuiBreeder(te, player)
//  def getContainer(te: TileBreeder, player: EntityPlayer): Container = new ContainerBreeder(te, player)
//}
