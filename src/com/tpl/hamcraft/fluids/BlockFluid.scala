package com.tpl.hamcraft.fluids

import com.tpl.hamcraft.HamCraftMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fluids.{BlockFluidClassic, Fluid}

class BlockFluid(val fluid: Fluid, val ownIcons: Boolean) extends BlockFluidClassic(fluid, Material.water) {
  setBlockName(HamCraftMod.modId + "." + fluid.getName)

  override def canDisplace(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = {
    world.getBlock(x, y, z).getMaterial.isLiquid
    if (world.getBlock(x, y, z).getMaterial.isLiquid) return false
    return super.canDisplace(world, x, y, z)
  }

  override def displaceIfPossible(world: World, x: Int, y: Int, z: Int): Boolean = {
    if (world.getBlock(x, y, z).getMaterial.isLiquid) return false
    return super.displaceIfPossible(world, x, y, z)
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(side: Int, meta: Int): IIcon = if (side == 0 || side == 1) fluid.getStillIcon else fluid.getFlowingIcon

  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(register: IIconRegister) {
    if (ownIcons) {
      fluid.setStillIcon(register.registerIcon(HamCraftMod.modId + ":fluids/" + fluid.getName + "/still"))
      fluid.setFlowingIcon(register.registerIcon(HamCraftMod.modId + ":fluids/" + fluid.getName + "/flowing"))
    }
  }
}
