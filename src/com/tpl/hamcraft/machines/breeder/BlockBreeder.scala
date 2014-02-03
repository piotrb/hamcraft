package com.tpl.hamcraft.machines.breeder

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.bdew.lib.block.HasTE
import net.bdew.lib.tile.inventory.BreakableInventoryBlock
import com.tpl.hamcraft.gui.BlockGuiWrenchable
import net.minecraft.util.Icon
import com.tpl.hamcraft.config.Machines
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister
import com.tpl.hamcraft.HamCraftMod

class BlockBreeder(id: Int) extends Block(id, Material.rock) with HasTE[TileBreeder] with BlockGuiWrenchable with BreakableInventoryBlock  {
  val TEClass = classOf[TileBreeder]
  private var icons: Array[Icon] = null
  lazy val guiId: Int = Machines.breeder.guiId

  setUnlocalizedName(HamCraftMod.modId + ".breeder")
  setHardness(5)

  override def getIcon(side: Int, meta: Int): Icon = {
    side match {
      case 0 =>
        return icons(0)
      case 1 =>
        return icons(1)
      case _ =>
        return icons(2)
    }
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IconRegister) {
    icons = Array(
      reg.registerIcon(HamCraftMod.modId + ":breeder/bottom"),
      reg.registerIcon(HamCraftMod.modId + ":breeder/top"),
      reg.registerIcon(HamCraftMod.modId + ":breeder/side")
    )
  }

}
