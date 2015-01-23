package com.tpl.hamcraft.fluids

import com.tpl.hamcraft.HamCraftMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item
import net.minecraftforge.fluids.Fluid

class ItemFluidCan(fluid: Fluid) extends Item {
  setUnlocalizedName(HamCraftMod.modId + "." + fluid.getName.toLowerCase + ".can")

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IIconRegister) {
    itemIcon = reg.registerIcon(HamCraftMod.modId + ":can/" + fluid.getName.toLowerCase)
  }
}
