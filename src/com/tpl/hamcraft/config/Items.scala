package com.tpl.hamcraft.config

import net.bdew.lib.config.ItemManager
import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.items.{EmancipatedAnimal, Emancipator}
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.{Item, ItemStack}
import com.tpl.hamcraft.fluids.babyfood.ItemBabyFoodBucket
import net.minecraftforge.fluids.FluidContainerRegistry

object Items extends ItemManager(Config.IDs) {

//  val mutagenCan = regItemCls(classOf[ItemMutagenCan], "MutagenCan")
//
//  FluidContainerRegistry.registerFluidContainer(Fluids.mutagen, new ItemStack(mutagenBucket), new ItemStack(Item.bucketEmpty))
//  FluidContainerRegistry.registerFluidContainer(Fluids.mutagen, new ItemStack(mutagenCan), ItemInterface.getItem("canEmpty"))
//
//  val geneSample = regItem(new GeneSample(ids.getItemId("GeneSample")))
//  val geneTemplate = regItem(new GeneTemplate(ids.getItemId("GeneTemplate")))
//
//  val upgradeItem = regItemCls(classOf[ItemApiaryUpgrade], "ApiaryUpgrade")
//
//  val grafter = regItemCls(classOf[IndustrialGrafter], "IndustrialGrafter", false)
//  GameRegistry.registerCustomItemStack("IndustrialGrafter", grafter.stackWithCharge(0))
//
//  regSimpleItem("MutagenTank")
//  regSimpleItem("BeeReceptacle")
//  regSimpleItem("PowerModule")
//  regSimpleItem("GeneticsProcessor")
//  regSimpleItem("UpgradeFrame")
//  regSimpleItem("ClimateModule")
//
//  val labware = regSimpleItem("Labware")
//  val waste = regSimpleItem("Waste")
//  val geneSampleBlank = regSimpleItem("GeneSampleBlank")

  val babyfoodBucket = regItemCls(classOf[ItemBabyFoodBucket], "BabyFoodBucket")
  FluidContainerRegistry.registerFluidContainer(Fluids.babyfood, new ItemStack(babyfoodBucket), new ItemStack(Item.bucketEmpty))

  val emancipator = regItemCls(classOf[Emancipator], "Emancipator", false)
  GameRegistry.registerCustomItemStack("Emancipator", emancipator.stackWithCharge(0))

  val emancipatedAnimal = regItemCls(classOf[EmancipatedAnimal], "EmancipatedAnimal", false)
  GameRegistry.registerCustomItemStack("EmancipatedAnimal", new ItemStack(emancipatedAnimal))

  HamCraftMod.logInfo("Items loaded")
}
