//package com.tpl.hamcraft.machines.breeder
//
//import net.minecraft.entity.player.EntityPlayer
//import com.tpl.hamcraft.HamCraftMod
//import com.tpl.hamcraft.gui.Textures
//import net.bdew.lib.gui.widgets.{WidgetProgressBar, WidgetFluidGauge, WidgetLabel}
//import net.bdew.lib.gui.{Texture, Color, Rect, BaseScreen}
//import net.bdew.lib.Misc
//import net.bdew.lib.power.WidgetPowerGauge
//
//class GuiBreeder(val te: TileBreeder, player: EntityPlayer) extends BaseScreen(new ContainerBreeder(te, player), 176, 184) {
//  val background = Texture.apply(HamCraftMod.modId, "textures/gui/breeder.png", Rect(0,0,256,256))
//  override def initGui() {
//    super.initGui()
//    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.breeder.name"), 8, 6, Color(0.25F, 0.25F, 0.25F)))
//
//    widgets.add(new WidgetProgressBar(new Rect(73, 29, 66, 15), Textures.whiteProgress(66), te.progress))
//
//    widgets.add(new WidgetPowerGauge(new Rect(8, 19, 16, 58), Textures.powerFill, te.power))
//
//    // water
//    widgets.add(new WidgetFluidGauge(new Rect(26, 19, 16, 58), Textures.tankOverlay, te.inputTank))
//  }
//}
