package com.tpl.hamcraft.machines.milker

import net.minecraft.entity.player.EntityPlayer
import com.tpl.hamcraft.HamCraftMod
import com.tpl.lib.gui.config.GuiConfig
import com.tpl.lib.gui.{WidgetFluidGaugeDynamic, WidgetPowerGaugeDynamic, BaseDynamicScreen}

class GuiMilkerConfig extends GuiConfig("milker", HamCraftMod.modId) {}

class GuiMilker(val te: TileMilker, player: EntityPlayer) extends BaseDynamicScreen(new ContainerMilker(te, player), te.config) {
  override def initGui() {
    super.initGui()

    widgets.add(config.getLabelWidget("title"))

//    widgets.add(new WidgetProgressBar(new Rect(82, 18, 66, 15), Textures.whiteProgress(66), te.progress))
//    widgets.add(new WidgetInventorySlot(100, 100, Textures.slotSelect))

    widgets.add(config.getFluidGauge("fluidGauge", te.inputTank, classOf[WidgetFluidGaugeDynamic]))
    widgets.add(config.getPowerGauge("powerGauge", te.power, classOf[WidgetPowerGaugeDynamic]))

    widgets.add(config.getPlayerInventoryWidget("player_inventory"))
  }
}
