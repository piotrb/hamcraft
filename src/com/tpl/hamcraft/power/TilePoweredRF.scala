package com.tpl.hamcraft.power

import cpw.mods.fml.common.Optional
import net.bdew.lib.power.TilePoweredBase
import cofh.api.energy.IEnergyHandler
import net.minecraftforge.common.ForgeDirection
import com.tpl.hamcraft.compat.PowerProxy
import com.tpl.hamcraft.config.Tuning

@Optional.Interface(modid = PowerProxy.TE_MOD_ID, iface = "cofh.api.energy.IEnergyHandler")
trait TilePoweredRF extends TilePoweredBase with IEnergyHandler {
  private lazy val ratio = Tuning.getSection("Power").getFloat("RF_MJ_Ratio")

  def receiveEnergy(from: ForgeDirection, maxReceive: Int, simulate: Boolean) =
    (power.inject(maxReceive / ratio, simulate) * ratio).floor.toInt

  def extractEnergy(from: ForgeDirection, maxExtract: Int, simulate: Boolean) = 0
  def canInterface(from: ForgeDirection) = PowerProxy.RFEnabled
  def getEnergyStored(from: ForgeDirection) = (power.stored * ratio).floor.toInt
  def getMaxEnergyStored(from: ForgeDirection) = (power.capacity * ratio).floor.toInt
}
