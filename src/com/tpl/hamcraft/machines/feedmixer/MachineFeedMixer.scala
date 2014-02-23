package com.tpl.hamcraft.machines.feedmixer

import net.bdew.lib.machine.{ProcessorMachine, Machine}
import net.bdew.lib.gui.GuiProvider
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container

class MachineFeedMixer extends Machine("FeedMixer", new BlockFeedMixer(_)) with GuiProvider with ProcessorMachine {
  def guiId = 2
  type TEClass = TileFeedMixer

  lazy val tankSize = tuning.getInt("TankSize")
  lazy val milkPerRun = tuning.getInt("milkPerRun")
  lazy val feedPerRun = tuning.getInt("feedPerRun")

  @SideOnly(Side.CLIENT)
  def getGui(te: TileFeedMixer, player: EntityPlayer): GuiContainer = new GuiFeedMixer(te, player)
  def getContainer(te: TileFeedMixer, player: EntityPlayer): Container = new ContainerFeedMixer(te, player)
}
