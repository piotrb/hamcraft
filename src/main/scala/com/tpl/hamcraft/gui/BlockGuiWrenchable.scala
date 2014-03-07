package com.tpl.hamcraft.gui

import cpw.mods.fml.common.Optional
import com.tpl.hamcraft.compat.PowerProxy
import net.minecraft.block.Block
import cofh.api.block.IDismantleable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.item.ItemStack
import net.bdew.lib.tile.inventory.BreakableInventoryTile
import net.bdew.lib.items.ItemUtils
import com.tpl.hamcraft.HamCraftMod
import buildcraft.api.tools.IToolWrench

@Optional.Interface(modid = PowerProxy.TE_MOD_ID, iface = "cofh.api.block.IDismantleable")
trait BlockGuiWrenchable extends Block with IDismantleable {
  val guiId: Int

  def dismantleBlock(player: EntityPlayer, world: World, x: Int, y: Int, z: Int, returnBlock: Boolean): ItemStack = {
    val item = new ItemStack(this)
    val te = world.getBlockTileEntity(x, y, z)

    if (te != null && te.isInstanceOf[BreakableInventoryTile])
      te.asInstanceOf[BreakableInventoryTile].dropItems()

    world.setBlockToAir(x, y, z)

    if (!returnBlock)
      ItemUtils.throwItemAt(world, x, y, z, item)

    item
  }

  def canDismantle(player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Boolean = true

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, meta: Int, xoffs: Float, yoffs: Float, zoffs: Float): Boolean = {
    if (player.isSneaking) {
      val equipped = if (player.getCurrentEquippedItem != null) player.getCurrentEquippedItem.getItem else null
      equipped match {
        case wrench: IToolWrench if wrench.canWrench(player, x, y, z) =>
          if (!world.isRemote) world.destroyBlock(x, y, z, true)
          return true
      }
      false
    } else {
      if (!world.isRemote) player.openGui(HamCraftMod.instance, guiId, world, x, y, z)
      true
    }
  }
}
