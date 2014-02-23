package com.tpl.hamcraft.machines.breeder

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import com.tpl.hamcraft.HamCraftMod
import net.bdew.lib.gui.widgets.WidgetLabel
import net.bdew.lib.gui.BaseScreen
import net.bdew.lib.Misc

class GuiIncubator(val te: TileIncubator, player: EntityPlayer) extends BaseScreen(new ContainerIncubator(te, player), 176, 184) {
  val texture: ResourceLocation = new ResourceLocation(HamCraftMod.modId + ":textures/gui/incubator.png")
  override def initGui() {
    super.initGui()
    widgets.add(new WidgetLabel(Misc.toLocal("tile.hamcraft.incubator.name"), 8, 6, 4210752))
  }
}
