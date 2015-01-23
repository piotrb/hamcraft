package com.tpl.hamcraft.machines.milker

import com.tpl.hamcraft.gui.Textures
import com.tpl.lib.gui.{WidgetFluidGaugeDynamic, WidgetPowerGaugeDynamic, WidgetInventorySlot, BaseDynamicScreen}
import net.bdew.lib.gui.Rect
import net.bdew.lib.gui.widgets.WidgetProgressBar
import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.HamCraftMod
import com.tpl.lib.gui.config.GuiConfig

class GuiMilkerConfig extends GuiConfig("milker", HamCraftMod.modId) {}

class GuiMilker(val te: TileMilker, player: EntityPlayer) extends BaseDynamicScreen(new ContainerMilker(te, player), te.config) {
  override def initGui() {
    super.initGui()

    widgets.add(config.getLabelWidget("title"))

    widgets.add(new WidgetProgressBar(new Rect(82, 18, 66, 15), Textures.whiteProgress(66), te.progress))
    widgets.add(new WidgetInventorySlot(100, 100, Textures.slotSelect))

    widgets.add(config.getFluidGauge("fluidGauge", te.inputTank, classOf[WidgetFluidGaugeDynamic]))
    widgets.add(config.getPowerGauge("powerGauge", te.power, classOf[WidgetPowerGaugeDynamic]))

    widgets.add(config.getPlayerInventoryWidget("player_inventory"))
  }
}
