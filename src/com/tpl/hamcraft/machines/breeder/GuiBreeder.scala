package com.tpl.hamcraft.machines.breeder

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.BaseScreen
import net.minecraft.util.ResourceLocation
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.gui.{Textures, WidgetPowerCustom, WidgetProgressBarNEI}
import net.bdew.lib.gui.widgets.{WidgetFluidGauge, WidgetLabel}
import net.bdew.lib.gui.{Rect, BaseScreen}
import net.bdew.lib.Misc

class GuiBreeder(val te: TileBreeder, player: EntityPlayer) extends BaseScreen(new ContainerBreeder(te, player), 176, 166) {
  val texture: ResourceLocation = new ResourceLocation(HamCraftMod.modId + ":textures/gui/breeder.png")
  override def initGui() {
    super.initGui()
    widgets.add(new WidgetProgressBarNEI(new Rect(69, 57, 66, 15), Textures.whiteProgress(66), te.progress, "Breeder"))
    widgets.add(new WidgetPowerCustom(new Rect(8, 19, 16, 58), Textures.powerFill, te.power))
    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.breeder.name"), 8, 6, 4210752))
    widgets.add(new WidgetFluidGauge(new Rect(33, 19, 16, 58), Textures.tankOverlay, te.tank))
  }
}
