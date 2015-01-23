package com.tpl.hamcraft.items

import java.util

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.config.Tuning
import com.tpl.hamcraft.power.ItemPowered
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.Misc
import net.bdew.lib.items.{ItemUtils, NamedItem}
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack, ItemTool}

object Emancipator extends ItemTool(0, Item.ToolMaterial.IRON, new util.HashSet[Block]) with ItemPowered with NamedItem {
  lazy val cfg = Tuning.getSection("Items").getSection("Emancipator")
  lazy val mjPerCharge = cfg.getInt("MjPerCharge")
  lazy val maxCharge = cfg.getInt("Charges") * mjPerCharge

  def name = "Emancipator"

  setUnlocalizedName(HamCraftMod.modId + ".emancipator")
  setMaxStackSize(1)
  setMaxDamage(101)

  efficiencyOnProperMaterial = 32

  override def itemInteractionForEntity(stack: ItemStack, player: EntityPlayer, entity: EntityLivingBase): Boolean = {
    if (!player.worldObj.isRemote) {
      if (hasCharges(stack) && EmancipatedAnimal.canUseOnEntity(player, entity)) {
        player.swingItem()
        emancipateAnimal(player, entity)
        useCharge(stack, 1, player)
        return true
      }
    }
    false
  }

  def emancipateAnimal(player: EntityPlayer, entity: EntityLivingBase) {
    val item = EmancipatedAnimal.createForEntity(player, entity)
    if (item != null) {
      ItemUtils.throwItemAt(player.getEntityWorld, entity.posX.toInt, entity.posY.toInt, entity.posZ.toInt, item)
      entity.setDead()
    }
  }

  override def hitEntity(stack: ItemStack, target: EntityLivingBase, player: EntityLivingBase): Boolean = false

  override def addInformation(stack: ItemStack, player: EntityPlayer, l: util.List[_], par4: Boolean) = {
    import scala.collection.JavaConverters._
    val tip = l.asInstanceOf[util.List[String]].asScala

    tip += Misc.toLocalF("hamcraft.label.charges", getCharge(stack) / mjPerCharge)
  }

  override def getSubItems(item: Item, tabs: CreativeTabs, l: util.List[_]) {
    import scala.collection.JavaConverters._
    val items = l.asInstanceOf[util.List[ItemStack]].asScala
    items += new ItemStack(this)
    items += stackWithCharge(maxCharge)
  }

  override def getItemEnchantability: Int = 0

  override def getIsRepairable(par1ItemStack: ItemStack, par2ItemStack: ItemStack): Boolean = false

  override def isBookEnchantable(itemstack1: ItemStack, itemstack2: ItemStack): Boolean = false

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IIconRegister) {
    itemIcon = reg.registerIcon(HamCraftMod.modId + ":emancipator")
  }
}
