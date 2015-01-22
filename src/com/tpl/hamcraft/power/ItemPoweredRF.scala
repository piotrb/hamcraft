package com.tpl.hamcraft.power

import com.tpl.hamcraft.compat.PowerProxy
import cpw.mods.fml.common.Optional
import net.bdew.lib.power.ItemPoweredBase
import cofh.api.energy.IEnergyContainerItem
import com.tpl.hamcraft.config.Tuning
import net.minecraft.item.ItemStack
import net.bdew.lib.Misc

@Optional.Interface(modid = PowerProxy.TE_MOD_ID, iface = "cofh.api.energy.IEnergyContainerItem")
trait ItemPoweredRF extends ItemPoweredBase with IEnergyContainerItem {
  private lazy val ratio = Tuning.getSection("Power").getFloat("RF_MJ_Ratio")

  def receiveEnergy(container: ItemStack, maxReceive: Int, simulate: Boolean): Int = {
    val charge = getCharge(container)
    val canCharge = Misc.clamp(maxCharge.toFloat - charge, 0F, maxReceive.toFloat / ratio).floor.toInt
    if (!simulate) setCharge(container, charge + canCharge)
    (canCharge * ratio).round
  }

  def extractEnergy(container: ItemStack, maxExtract: Int, simulate: Boolean): Int = 0
  def getEnergyStored(container: ItemStack): Int = (getCharge(container) * ratio).round
  def getMaxEnergyStored(container: ItemStack): Int = (maxCharge * ratio).round
}
