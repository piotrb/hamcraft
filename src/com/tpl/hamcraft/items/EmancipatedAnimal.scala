package com.tpl.hamcraft.items

import net.minecraft.item.{Item, ItemStack}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.util.Facing
import net.minecraft.entity.passive._
import java.util
import java.util.List
import net.bdew.lib.Misc
import cpw.mods.fml.common.FMLLog
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister
import com.tpl.hamcraft.HamCraftMod
import codechicken.lib.data.NBTDataWrapper
import net.minecraft.entity.{EntityCreature, passive, EntityLivingBase, Entity}
import net.minecraft.nbt.NBTTagCompound
import cpw.mods.fml.common.registry.EntityRegistry
import com.tpl.hamcraft.config.Items
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.nbt
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.util.Icon
import scala.collection.mutable

class EmancipatedAnimal(id: Int) extends Item(id) {

  type EntityClass = Class[_ <: EntityLivingBase]

  @SideOnly(Side.CLIENT)
  var subTypeIcons: mutable.Map[String, Icon] = mutable.Map.empty[String, Icon]

  setMaxStackSize(16)
  setMaxDamage(10)
  setUnlocalizedName(HamCraftMod.modId + ".emancipatedanimal")

  setHasSubtypes(true)

  def entityToSpawn(stack: ItemStack, world: World): EntityCreature = {
    val typeString = getAnimalType(stack)
    if (EmancipatedAnimal.nameEntityMap.contains(typeString)) {
      val entityClass: EntityClass = EmancipatedAnimal.nameEntityMap(typeString)
      entityClass.getConstructor(classOf[World]).newInstance(world).asInstanceOf[EntityCreature]
    } else {
      null
    }
  }

  def onCreateFromEntity(stack: ItemStack, entity: EntityLivingBase, player: EntityPlayer) {
    onCreateSubtype(stack, EmancipatedAnimal.nameForEntity(entity), entity.isChild)
  }

  def onCreateSubtype(stack: ItemStack, name: String, child: Boolean = false) {
    stack.stackTagCompound = new nbt.NBTTagCompound()
    stack.stackTagCompound.setString("entityName", name)
    stack.stackTagCompound.setBoolean("child", child)
  }

  override def onCreated(stack: ItemStack, world: World, player: EntityPlayer) {
    stack.stackTagCompound = new NBTTagCompound()
  }

  override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float) = {
    if (!world.isRemote) {
      val _x = x + Facing.offsetsXForSide(side)
      val _y = y + Facing.offsetsYForSide(side)
      val _z = z + Facing.offsetsZForSide(side)
      val entity =  entityToSpawn(stack, world)
      entity.setLocationAndAngles(_x.toDouble + 0.5D, _y.toDouble + 0.5D, _z.toDouble + 0.5D, 0, 0)
      world.spawnEntityInWorld(entity)
      entity.playLivingSound()
      if (!player.capabilities.isCreativeMode) {
        stack.stackSize -= 1
      }
    }
    false
  }

  def getAnimalType(stack: ItemStack): String = {
    if(stack.getTagCompound != null) {
      val name = stack.getTagCompound.getString("entityName")
      if(name != "") return name
    }
    return "invalid"
  }

  def isChild(stack: ItemStack): Boolean = {
    if(stack.getTagCompound != null) {
      return stack.stackTagCompound.getBoolean("child")
    }
    return false
  }

  override def addInformation(stack: ItemStack, player: EntityPlayer, list: util.List[_], par4: Boolean) {
    import scala.collection.JavaConverters._
    val tip = list.asInstanceOf[util.List[String]].asScala

    tip += Misc.toLocalF("hamcraft.label.health", getMaxDamage(stack) - getDamage(stack), getMaxDamage(stack))
    var line = Misc.toLocalF("hamcraft.animal." + getAnimalType(stack))
    if(isChild(stack)) {
      line += " (" + Misc.toLocalF("hamcraft.label.child") + ")"
    }
    tip += line
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(stack: ItemStack, pass: Int): Icon = {
    subTypeIcons(getAnimalType(stack))
  }

  @SideOnly(Side.CLIENT)
  override def getIconIndex(stack: ItemStack): Icon = subTypeIcons(getAnimalType(stack))


  override def getUnlocalizedName(stack: ItemStack): String = {
    getUnlocalizedName() + "." + getAnimalType(stack)
  }

  override def getSubItems(id: Int, tab: CreativeTabs, list: util.List[_]) = {
    val l = list.asInstanceOf[java.util.List[ItemStack]]
    EmancipatedAnimal.validSubtypes.foreach { name =>
      var stack = new ItemStack(id, 1, 0)
      onCreateSubtype(stack, name, false)
      l.add(stack)
    }
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IconRegister) {
    EmancipatedAnimal.allSubtypes.foreach { name =>
      subTypeIcons(name) = reg.registerIcon(HamCraftMod.modId + ":emancipatedanimal/" + name)
    }
  }
}

object EmancipatedAnimal {

  type EntityClass = Class[_ <: EntityLivingBase]

  private val validSubtypes = mutable.Set[String]()
  private val nameEntityMap = mutable.Map[String, EntityClass]()
  private val entityNameMap = mutable.Map[EntityClass, String]()

  addEntity("pig", classOf[EntityPig])
  addEntity("sheep", classOf[EntitySheep])
  addEntity("cow", classOf[EntityCow])
  addEntity("squid", classOf[EntitySquid])

  def allSubtypes: Set[String] = validSubtypes.union(mutable.Set("invalid")).toSet

  def addEntity(name: String, entityClass: EntityClass) {
    validSubtypes.add(name)
    nameEntityMap(name) = entityClass
    entityNameMap(entityClass) = name
  }

  def nameForEntity(entity: EntityLivingBase) = {
    val entityClass = entity.getClass
    if (entityNameMap.contains(entityClass)) {
      entityNameMap(entityClass)
    } else {
      "invalid"
    }
  }

  def canUseOnEntity(player: EntityPlayer, entity: EntityLivingBase): Boolean = {
    if(entity.isChild)
      return false
    nameForEntity(entity) != "invalid"
  }

  def createForEntity(player: EntityPlayer, entity: EntityLivingBase): ItemStack = {
    val stack = new ItemStack(Items.emancipatedAnimal)
    Items.emancipatedAnimal.onCreateFromEntity(stack, entity, player)
    stack
  }

  def damageStack(stack: ItemStack, amount: Int) {
    stack.setItemDamage(stack.getItemDamage + amount)
    if(stack.getItemDamage > stack.getMaxDamage) {
      stack.stackSize -= 1
    }
  }

  def makeChild(parent1: ItemStack, parent2: ItemStack): ItemStack = {
    damageStack(parent1, 1)
    damageStack(parent2, 1)
    val stack = new ItemStack(Items.emancipatedAnimal)
    Items.emancipatedAnimal.onCreateSubtype(stack, Items.emancipatedAnimal.getAnimalType(parent1), true)
    stack.setItemDamage(stack.getMaxDamage)
    stack
  }
}
