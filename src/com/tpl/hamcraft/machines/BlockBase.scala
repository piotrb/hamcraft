package com.tpl.hamcraft.machines

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.bdew.lib.block.HasTE
import com.tpl.hamcraft.gui.BlockGuiWrenchable
import net.bdew.lib.tile.inventory.BreakableInventoryBlock
import com.tpl.hamcraft.HamCraftMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import net.minecraft.tileentity.TileEntity
import scala.reflect.{ClassTag, classTag}
import net.bdew.lib.rotate.{IconType, RotateableTileBlock}

abstract class BlockBase[T <: TileEntity](val TEClass: Class[_ <: TileEntity], id: Int, shortName: String) extends Block(id, Material.iron) with HasTE[T] with BlockGuiWrenchable with BreakableInventoryBlock with RotateableTileBlock {
  protected var icons: Array[Icon] = null

  def getIcon(meta: Int, kind: IconType.Value) = kind match {
    case IconType.FRONT => icons(0)
    case IconType.BACK => icons(1)
    case IconType.SIDE => icons(2)
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IconRegister) {
    registerIcons(reg, Array("front", "back", "side"))
  }

  setUnlocalizedName(HamCraftMod.modId + "." + shortName)
  setHardness(5)

  @SideOnly(Side.CLIENT)
  protected def registerIcon(reg: IconRegister, name: String): Icon = {
    reg.registerIcon(HamCraftMod.modId + ":" + shortName + "/" + name)
  }

  @SideOnly(Side.CLIENT)
  protected def registerIcons(reg: IconRegister, names: Array[String]) {
    icons = names.map((name: String) => registerIcon(reg, name))
  }

}