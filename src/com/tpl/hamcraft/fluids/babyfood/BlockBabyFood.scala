package com.tpl.hamcraft.fluids.babyfood

import cpw.mods.fml.relauncher.SideOnly
import net.minecraftforge.fluids.BlockFluidClassic
import com.tpl.hamcraft.config.{Fluids,Items}
import net.minecraft.block.material.Material
import cpw.mods.fml.relauncher.Side
import net.minecraft.util.Icon
import com.tpl.hamcraft.HamCraftMod
import net.minecraftforge.common.MinecraftForge
import net.minecraft.world.{World, IBlockAccess}
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraftforge.event.entity.player.FillBucketEvent
import net.minecraftforge.event.Event.Result
import net.minecraftforge.event.ForgeSubscribe
import net.minecraft.item.ItemStack

class BlockBabyFood(id: Int) extends BlockFluidClassic(id, Fluids.babyfood, Material.water) {

  protected var stillIcon: Icon = null
  protected var flowingIcon: Icon = null

  Fluids.babyfood.setBlockID(id)
  setUnlocalizedName(HamCraftMod.modId + ".babyfood")
  MinecraftForge.EVENT_BUS.register(this)

  override def colorMultiplier(iblockaccess: IBlockAccess, x: Int, y: Int, z: Int): Int = 0x7c5e01

  override def canDisplace(world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = {
    if (world.getBlockMaterial(x, y, z).isLiquid) return false
    super.canDisplace(world, x, y, z)
  }

  override def displaceIfPossible(world: World, x: Int, y: Int, z: Int): Boolean = {
    if (world.getBlockMaterial(x, y, z).isLiquid) return false
    super.displaceIfPossible(world, x, y, z)
  }

  @ForgeSubscribe
  def onBucketFill(event: FillBucketEvent) {
    if (event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) != 0) return
    if (event.world.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ) != blockID) return
    event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ)
    event.result = new ItemStack(Items.babyfoodBucket)
    event.setResult(Result.ALLOW)
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(side: Int, meta: Int): Icon = if (side == 0 || side == 1) stillIcon else flowingIcon


  @SideOnly(Side.CLIENT)
  override def registerIcons(register: IconRegister) {
    stillIcon = register.registerIcon(HamCraftMod.modId + ":babyfood/still")
    flowingIcon = register.registerIcon(HamCraftMod.modId + ":babyfood/flowing")
    Fluids.babyfood.setIcons(stillIcon, flowingIcon)
  }

}
