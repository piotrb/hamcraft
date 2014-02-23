package com.tpl.hamcraft.machines.incubator

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import com.tpl.hamcraft.HamCraftMod
import net.bdew.lib.gui.widgets.{WidgetProgressBar, WidgetFluidGauge, WidgetLabel}
import net.bdew.lib.gui.{Rect, BaseScreen}
import net.bdew.lib.Misc
import com.tpl.hamcraft.gui.{Textures, WidgetPowerCustom}

class GuiIncubator(val te: TileIncubator, player: EntityPlayer) extends BaseScreen(new ContainerIncubator(te, player), 176, 184) {
  val texture: ResourceLocation = new ResourceLocation(HamCraftMod.modId + ":textures/gui/incubator.png")
  override def initGui() {
    super.initGui()

    widgets.add(new WidgetProgressBar(new Rect(82, 18, 66, 15), Textures.whiteProgress(66), te.progress))

    widgets.add(new WidgetPowerCustom(new Rect(8, 18, 16, 58), Textures.powerFill, te.power))
    widgets.add(new WidgetFluidGauge(new Rect(26, 18, 16, 58), Textures.tankOverlay, te.inputTank))

    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.incubator.name"), 8, 6, 4210752))
  }
}
