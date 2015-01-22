package com.tpl.hamcraft.config

import net.bdew.lib.config.ItemManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.items.{EmancipatedAnimal, Emancipator}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.{Item, ItemStack}
import com.tpl.hamcraft.fluids.babyfood.ItemBabyFoodBucket
import net.minecraftforge.fluids.FluidContainerRegistry

object Items extends ItemManager(Config.IDs) {

  val babyfoodBucket = regItemCls(classOf[ItemBabyFoodBucket], "BabyFoodBucket")
  FluidContainerRegistry.registerFluidContainer(Fluids.babyfood, new ItemStack(babyfoodBucket), new ItemStack(Item.bucketEmpty))

  val emancipator = regItemCls(classOf[Emancipator], "Emancipator", addStack = false)
  GameRegistry.registerCustomItemStack("Emancipator", emancipator.stackWithCharge(0))

  val emancipatedAnimal = regItemCls(classOf[EmancipatedAnimal], "EmancipatedAnimal", addStack = false)
  GameRegistry.registerCustomItemStack("EmancipatedAnimal", new ItemStack(emancipatedAnimal))

  HamCraftMod.logInfo("Items loaded")
}
