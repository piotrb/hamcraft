package com.tpl.hamcraft

import cpw.mods.fml.common.FMLLog
import net.minecraftforge.oredict.OreDictionary
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.block.Block
import net.minecraftforge.fluids.FluidStack

object Util {

  def registerOreDictionary() {
    OreDictionary.registerOre("seedAny", new ItemStack(Item.seeds))
    OreDictionary.registerOre("seedAny", new ItemStack(Item.melonSeeds))
    OreDictionary.registerOre("seedAny", new ItemStack(Item.pumpkinSeeds))

    OreDictionary.registerOre("mushroomAny", new ItemStack(Block.mushroomRed))
    OreDictionary.registerOre("mushroomAny", new ItemStack(Block.mushroomBrown))

    OreDictionary.registerOre("wheat", new ItemStack(Item.wheat))

    OreDictionary.registerOre("meatRaw", new ItemStack(Item.beefRaw))
    OreDictionary.registerOre("meatRaw", new ItemStack(Item.porkRaw))
    OreDictionary.registerOre("meatRaw", new ItemStack(Item.fishRaw))

    OreDictionary.registerOre("foodRootVegetables", new ItemStack(Item.potato))
    OreDictionary.registerOre("foodRootVegetables", new ItemStack(Item.carrot))
  }

  def dumpOreDictionary() {
    FMLLog.info("**************************************")
    FMLLog.info("Dumping Ore Dictionary ...")
    FMLLog.info("")

    val ores = OreDictionary.getOreNames
    ores.foreach(oreName => {
      FMLLog.info("%s:", oreName)
      val oreList = OreDictionary.getOres(oreName).toArray
      oreList.foreach(_ore => {
        val ore = _ore.asInstanceOf[ItemStack]
        FMLLog.info("  - %s (%d)", ore.getDisplayName, ore.stackSize.asInstanceOf[AnyRef])
      })
      FMLLog.info("")
    })
    FMLLog.info("**************************************")
  }

  def debug(label: String, fluidStack: FluidStack) {
    FMLLog.info("%s: (id: %d) %s (%s) (%d)", label, fluidStack.fluidID.asInstanceOf[AnyRef], fluidStack.getFluid.getName, fluidStack.getFluid.getLocalizedName, fluidStack.amount.asInstanceOf[AnyRef])
  }

  def debug(label: String, itemStack: ItemStack) {
    if(itemStack == null) FMLLog.info("%s: null", label)
    else FMLLog.info("%s: %s (%s) (%d) %s", label, itemStack.getUnlocalizedName, itemStack.getDisplayName, itemStack.stackSize.asInstanceOf[AnyRef], itemStack.getTooltip(null, true).toString)
  }

  def debug(label: String, item: Item) {
    FMLLog.info("%s: %s", label, item.getUnlocalizedName)
  }

  def debug(label: String, int: Int) {
    FMLLog.info("%s: %d", label, int.asInstanceOf[AnyRef])
  }

  def debug(label: String, bool: Boolean) {
    FMLLog.info("%s: %s", label, bool.toString)
  }

  def debug(label: String, string: String) {
    FMLLog.info("%s: %s", label, string)
  }

  def debug(label: String) {
    FMLLog.info("%s", label)
  }

  def debug(label: String, array: Array[_]) {
    var string = ""
    array.foreach(i => {
      string = String.format("%s, %s", string, i.toString)
    })
    string = String.format("[%s]", string)
    FMLLog.info("%s: %s", label, string)
  }

}
