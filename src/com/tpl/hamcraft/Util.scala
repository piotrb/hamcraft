package com.tpl.hamcraft

import cpw.mods.fml.common.FMLLog
import net.minecraftforge.oredict.OreDictionary
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.block.Block

object Util {

  def registerOreDictionary {
    OreDictionary.registerOre("seedAny", new ItemStack(Item.seeds))
    OreDictionary.registerOre("seedAny", new ItemStack(Item.melonSeeds))
    OreDictionary.registerOre("seedAny", new ItemStack(Item.pumpkinSeeds))

    OreDictionary.registerOre("mushroomAny", new ItemStack(Block.mushroomRed))
    OreDictionary.registerOre("mushroomAny", new ItemStack(Block.mushroomBrown))

    OreDictionary.registerOre("wheat", new ItemStack(Item.wheat))

    OreDictionary.registerOre("meatRaw", new ItemStack(Item.beefRaw))
    OreDictionary.registerOre("meatRaw", new ItemStack(Item.porkRaw))

    OreDictionary.registerOre("foodRootVegetables", new ItemStack(Item.potato))
    OreDictionary.registerOre("foodRootVegetables", new ItemStack(Item.carrot))
  }

  def dumpOreDictionary {
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

}
