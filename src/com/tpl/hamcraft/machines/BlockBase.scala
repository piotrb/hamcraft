package com.tpl.hamcraft.machines

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.gui.BlockGuiWrenchable
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.block.HasTE
import net.bdew.lib.rotate.{RotatableTileBlock, IconType}
import net.bdew.lib.tile.inventory.BreakableInventoryBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon

abstract class BlockBase[T <: TileEntity](val TEClass: Class[_ <: TileEntity], shortName: String) extends Block(Material.iron) with HasTE[T] with BlockGuiWrenchable with BreakableInventoryBlock with RotatableTileBlock {
  protected var icons: Array[IIcon] = null

  def getIcon(meta: Int, kind: IconType.Value) = kind match {
    case IconType.FRONT => icons(0)
    case IconType.BACK => icons(1)
    case IconType.SIDE => icons(2)
  }

  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(reg: IIconRegister) {
    registerIcons(reg, Array("front", "back", "side"))
  }

  setBlockName(HamCraftMod.modId + "." + shortName)
  setHardness(5)

  @SideOnly(Side.CLIENT)
  protected def registerIcon(reg: IIconRegister, name: String): IIcon = {
    reg.registerIcon(HamCraftMod.modId + ":" + shortName + "/" + name)
  }

  @SideOnly(Side.CLIENT)
  protected def registerIcons(reg: IIconRegister, names: Array[String]) {
    icons = names.map((name: String) => registerIcon(reg, name))
  }

}
