package com.tpl.hamcraft.machines.feedmixer

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.{Rect, BaseScreen}
import net.minecraft.util.ResourceLocation
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.gui.{WidgetPowerCustom, Textures}
import net.bdew.lib.gui.widgets.{WidgetProgressBar, WidgetFluidGauge, WidgetLabel}
import net.bdew.lib.Misc

class GuiFeedMixer(val te: TileFeedMixer, player: EntityPlayer) extends BaseScreen(new ContainerFeedMixer(te, player), 176, 166) {
  val texture: ResourceLocation = new ResourceLocation(HamCraftMod.modId + ":textures/gui/feedmixer.png")

  override def initGui() {
    super.initGui()
    widgets.add(new WidgetProgressBar(new Rect(84, 18, 66, 15), Textures.whiteProgress(66), te.progress))
    widgets.add(new WidgetPowerCustom(new Rect(8, 13, 9, 27), Textures.powerFill, te.power))
    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.feedmixer.name"), 8, 4, 4210752))
    widgets.add(new WidgetFluidGauge(new Rect(19, 13, 9, 27), Textures.tankOverlay, te.inputTank))
    widgets.add(new WidgetFluidGauge(new Rect(159, 13, 9, 27), Textures.tankOverlay, te.outputTank))
  }
}
