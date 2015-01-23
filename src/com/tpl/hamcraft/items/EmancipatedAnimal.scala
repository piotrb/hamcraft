package com.tpl.hamcraft.items

import java.util

import com.tpl.hamcraft.HamCraftMod
import com.tpl.hamcraft.config.Tuning
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.Misc
import net.bdew.lib.items.NamedItem
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.passive._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{EntityCreature, EntityLivingBase}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{Facing, IIcon}
import net.minecraft.world.World

import scala.collection.mutable

class AnimalInfo(val name: String, val entityClass: Class[_ <: EntityLivingBase]) {
  lazy val cfg = EmancipatedAnimal.cfg.getSection(name)
  lazy val breedingDamage = cfg.getInt("breedingDamage")
  lazy val breedingFood = cfg.getString("breedingFood")
}

object EmancipatedAnimal extends Item with NamedItem {

  lazy val cfg = Tuning.getSection("Items").getSection("EmancipatedAnimal")
  private val entityTypes = mutable.Set[AnimalInfo]()

  setUnlocalizedName(HamCraftMod.modId + ".emancipatedanimal")

  setMaxStackSize(1)
  setMaxDamage(10)

  setHasSubtypes(true)
  @SideOnly(Side.CLIENT)
  var subTypeIcons: mutable.Map[String, IIcon] = mutable.Map.empty[String, IIcon]

  def name = "EmancipatedAnimal"

  override def onCreated(stack: ItemStack, world: World, player: EntityPlayer) {
    stack.stackTagCompound = new NBTTagCompound()
  }

  override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float) = {
    if (!world.isRemote) {
      val _x = x + Facing.offsetsXForSide(side)
      val _y = y + Facing.offsetsYForSide(side)
      val _z = z + Facing.offsetsZForSide(side)
      val entity = entityToSpawn(stack, world)
      entity.setLocationAndAngles(_x.toDouble + 0.5D, _y.toDouble + 0.5D, _z.toDouble + 0.5D, 0, 0)
      world.spawnEntityInWorld(entity)
      entity.playLivingSound()
      if (!player.capabilities.isCreativeMode) {
        stack.stackSize -= 1
      }
    }
    false
  }

  def entityToSpawn(stack: ItemStack, world: World) = {
    val typeString = getAnimalType(stack)
    val info = infoForSubtype(typeString)
    if (info.isDefined) {
      info.get.entityClass.getConstructor(classOf[World]).newInstance(world).asInstanceOf[EntityCreature]
    } else {
      null
    }
  }

  override def addInformation(stack: ItemStack, player: EntityPlayer, list: util.List[_], par4: Boolean) {
    import scala.collection.JavaConverters._
    val tip = list.asInstanceOf[util.List[String]].asScala

    tip += Misc.toLocalF("hamcraft.label.health", getMaxDamage(stack) - getDamage(stack), getMaxDamage(stack))
    var line = Misc.toLocalF("hamcraft.animal." + getAnimalType(stack))
    if (isChild(stack)) {
      line += " (" + Misc.toLocalF("hamcraft.label.child") + ")"
    }
    tip += line
  }

  def isChild(stack: ItemStack): Boolean = {
    if (stack.getTagCompound != null) {
      return stack.stackTagCompound.getBoolean("child")
    }
    false
  }

  @SideOnly(Side.CLIENT)
  override def getIcon(stack: ItemStack, pass: Int): IIcon = {
    subTypeIcons(getAnimalType(stack))
  }

  @SideOnly(Side.CLIENT)
  override def getIconIndex(stack: ItemStack): IIcon = subTypeIcons(getAnimalType(stack))

  override def getUnlocalizedName(stack: ItemStack): String = {
    super.getUnlocalizedName + "." + getAnimalType(stack)
  }

  override def getSubItems(item: Item, tab: CreativeTabs, list: util.List[_]) = {
    val l = list.asInstanceOf[java.util.List[ItemStack]]
    entityTypes.foreach(info => {
      val stack = new ItemStack(this, 1, 0)
      onCreateSubtype(stack, info.name, child = false)
      l.add(stack)
    })
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IIconRegister) {
    entityTypes.foreach { info =>
      subTypeIcons(info.name) = reg.registerIcon(HamCraftMod.modId + ":emancipatedanimal/" + info.name)
    }
    subTypeIcons("invalid") = reg.registerIcon(HamCraftMod.modId + ":emancipatedanimal/" + "invalid")
  }

  def addEntity(name: String, entityClass: Class[_ <: EntityLivingBase]) {
    entityTypes.add(new AnimalInfo(name, entityClass))
  }

  addEntity("chicken", classOf[EntityChicken])
  addEntity("pig", classOf[EntityPig])
  addEntity("sheep", classOf[EntitySheep])
  addEntity("cow", classOf[EntityCow])
  addEntity("squid", classOf[EntitySquid])

  def canUseOnEntity(player: EntityPlayer, entity: EntityLivingBase): Boolean = {
    if (entity.isChild)
      return false
    infoForEntity(entity).isDefined
  }

  def infoForEntity(entity: EntityLivingBase): Option[AnimalInfo] = {
    entityTypes.find(info => info.entityClass == entity.getClass)
  }

  def createForEntity(player: EntityPlayer, entity: EntityLivingBase): ItemStack = {
    val stack = new ItemStack(this)
    onCreateFromEntity(stack, entity, player)
    stack
  }

  def onCreateFromEntity(stack: ItemStack, entity: EntityLivingBase, player: EntityPlayer) {
    val info = infoForEntity(entity)
    var subtype = info match {
      case Some(_) => info.get.name
      case _ => "invalid"
    }
    onCreateSubtype(stack, subtype, entity.isChild)
  }

  def makeChild(parent1: ItemStack, parent2: ItemStack): ItemStack = {
    damageStack(parent1, infoForStack(parent1).get.breedingDamage)
    damageStack(parent2, infoForStack(parent2).get.breedingDamage)
    val stack = new ItemStack(this)
    onCreateSubtype(stack, getAnimalType(parent1), child = true)
    stack.setItemDamage(stack.getMaxDamage)
    stack
  }

  def onCreateSubtype(stack: ItemStack, name: String, child: Boolean = false) {
    stack.stackTagCompound = new nbt.NBTTagCompound()
    stack.stackTagCompound.setString("entityName", name)
    stack.stackTagCompound.setBoolean("child", child)
  }

  def infoForStack(stack: ItemStack) = {
    infoForSubtype(getAnimalType(stack))
  }

  def infoForSubtype(subtype: String): Option[AnimalInfo] = {
    entityTypes.find(info => info.name == subtype)
  }

  def getAnimalType(stack: ItemStack): String = {
    if (stack.getTagCompound != null) {
      val name = stack.getTagCompound.getString("entityName")
      if (name != "") return name
    }
    "invalid"
  }

  def damageStack(stack: ItemStack, amount: Int) {
    stack.setItemDamage(stack.getItemDamage + amount)
    if (stack.getItemDamage >= stack.getMaxDamage) {
      stack.stackSize -= 1
    }
  }

  def growUp(child: ItemStack) {
    child.stackTagCompound.setBoolean("child", false)
  }

  def breedingFood(itemStack: ItemStack): String = {
    val info = infoForSubtype(getAnimalType(itemStack))
    if (info.isEmpty) throw new Exception("no breeding food defined")
    info.get.breedingFood
  }
}
