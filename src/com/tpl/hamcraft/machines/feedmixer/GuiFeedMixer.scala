package com.tpl.hamcraft.machines.feedmixer

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.{Texture, Color, Rect, BaseScreen}
import net.minecraft.util.ResourceLocation
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.gui.{WidgetPowerCustom, Textures}
import net.bdew.lib.gui.widgets.{WidgetProgressBar, WidgetFluidGauge, WidgetLabel}
import net.bdew.lib.Misc
import net.bdew.lib.power.WidgetPowerGauge

class GuiFeedMixer(val te: TileFeedMixer, player: EntityPlayer) extends BaseScreen(new ContainerFeedMixer(te, player), 176, 166) {
  val background = Texture.apply(HamCraftMod.modId, "textures/gui/feedmixer.png", Rect(0,0,256,256))

  override def initGui() {
    super.initGui()
    widgets.add(new WidgetProgressBar(new Rect(84, 18, 66, 15), Textures.whiteProgress(66), te.progress))
    widgets.add(new WidgetPowerGauge(new Rect(8, 13, 9, 27), Textures.powerFill, te.power))
    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.feedmixer.name"), 8, 4, Color(0.25F, 0.25F, 0.25F)))
    widgets.add(new WidgetFluidGauge(new Rect(19, 13, 9, 27), Textures.tankOverlay, te.inputTank))
    widgets.add(new WidgetFluidGauge(new Rect(159, 13, 9, 27), Textures.tankOverlay, te.outputTank))
  }
}
