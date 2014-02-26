package com.tpl.hamcraft.machines.milker

import net.minecraft.entity.player.EntityPlayer
import net.bdew.lib.gui.widgets._
import net.bdew.lib.Misc
import com.tpl.hamcraft.gui._
import net.bdew.lib.gui.Rect

class GuiMilker(val te: TileMilker, player: EntityPlayer) extends BaseDynamicScreen(new ContainerMilker(te, player), 176, 165, Textures.guiFrame) {

  override def initGui() {
    super.initGui()

    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.milker.name"), 8, 6, 4210752))
//    widgets.add(new WidgetLabel(cfg("title"))

//    widgets.add(new WidgetProgressBar(new Rect(82, 18, 66, 15), Textures.whiteProgress(66), te.progress))
//    widgets.add(new WidgetInventorySlot(100, 100, Textures.slotSelect))

//    widgets.add(new WidgetPowerCustom(new Rect(8, 18, 16, 58), Textures.powerFill, te.power))
//    widgets.add(new WidgetFluidGauge(new Rect(26, 18, 16, 58), Textures.tankOverlay, te.inputTank))

    // width: 162, height: 76
    widgets.add(new WidgetPlayerInventory(7, 83, Textures.inventorySlot, new ColorAlpha(1,0,0,0.5F), new ColorAlpha(0,1,0,0.5F)))
  }
}
