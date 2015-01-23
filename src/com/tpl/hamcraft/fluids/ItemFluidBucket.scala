package com.tpl.hamcraft.fluids

import com.tpl.hamcraft.HamCraftMod
import cpw.mods.fml.common.eventhandler.Event.Result
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.{ItemBucket, ItemStack}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.FillBucketEvent
import net.minecraftforge.fluids.Fluid

class ItemFluidBucket(fluid: Fluid) extends ItemBucket(fluid.getBlock) {
  setUnlocalizedName(HamCraftMod.modId + "." + fluid.getName.toLowerCase + ".bucket")

  setContainerItem(GameRegistry.findItem("minecraft", "bucket"))

  MinecraftForge.EVENT_BUS.register(this)

  @SubscribeEvent
  def onBucketFill(event: FillBucketEvent) {
    if (event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) != 0) return
    if (event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) != fluid.getBlock) return
    event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ)
    event.result = new ItemStack(this)
    event.setResult(Result.ALLOW)
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IIconRegister) {
    itemIcon = reg.registerIcon(HamCraftMod.modId + ":bucket/" + fluid.getName.toLowerCase)
  }
}
