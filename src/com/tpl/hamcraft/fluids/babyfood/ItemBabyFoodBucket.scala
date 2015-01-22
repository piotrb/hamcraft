package com.tpl.hamcraft.fluids.babyfood

import net.minecraft.item.ItemBucket
import com.tpl.hamcraft.config.Blocks
import com.tpl.hamcraft.HamCraftMod
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister

class ItemBabyFoodBucket(id: Int) extends ItemBucket(id, Blocks.babyfood.blockID) {
  setUnlocalizedName(HamCraftMod.modId + ".babyfood.bucket")

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IconRegister) {
    itemIcon = reg.registerIcon(HamCraftMod.modId + ":babyfood/bucket")
  }
}