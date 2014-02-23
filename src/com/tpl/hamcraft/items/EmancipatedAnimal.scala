package com.tpl.hamcraft.items

import net.minecraft.item.{Item, ItemStack}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.util.Facing
import net.minecraft.entity.passive._
import java.util
import net.bdew.lib.Misc
import net.minecraft.client.renderer.texture.IconRegister
import com.tpl.hamcraft.HamCraftMod
import net.minecraft.entity.{EntityCreature, EntityLivingBase}
import net.minecraft.nbt.NBTTagCompound
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

  implicit def getAnimalType(stack: ItemStack) = EmancipatedAnimal.getAnimalType(stack)
  implicit def infoForSubtype(name: String) = EmancipatedAnimal.infoForSubtype(name)
  implicit def infoForEntity(entity: EntityLivingBase) = EmancipatedAnimal.infoForEntity(entity)
  implicit def entityTypes = EmancipatedAnimal.entityTypes

  def entityToSpawn(stack: ItemStack, world: World): EntityCreature = {
    val typeString = getAnimalType(stack)
    val info = infoForSubtype(typeString)
    if (info.isDefined) {
      info.get.entityClass.getConstructor(classOf[World]).newInstance(world).asInstanceOf[EntityCreature]
    } else {
      null
    }
  }

  def onCreateFromEntity(stack: ItemStack, entity: EntityLivingBase, player: EntityPlayer) {
    val info = infoForEntity(entity)
    val subtype = info match {
      case Some(_) => info.get.name
      case _ => "invalid"
    }
    onCreateSubtype(stack, subtype, entity.isChild)
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
    entityTypes.foreach( info => {
      var stack = new ItemStack(id, 1, 0)
      onCreateSubtype(stack, info.name, false)
      l.add(stack)
    })
  }

  @SideOnly(Side.CLIENT)
  override def registerIcons(reg: IconRegister) {
    entityTypes.foreach { info =>
      subTypeIcons(info.name) = reg.registerIcon(HamCraftMod.modId + ":emancipatedanimal/" + info.name)
    }
    subTypeIcons("invalid") = reg.registerIcon(HamCraftMod.modId + ":emancipatedanimal/" + "invalid")
  }
}

object EmancipatedAnimal {

  trait AnimalBase {
    val name: String
    val entityClass: Class[_ <: EntityLivingBase]
    val breedingFood: String
  }

  object Pig extends AnimalBase {
    val name = "pig"
    val entityClass = classOf[EntityPig]
    val breedingFood = "foodRootVegetables"
  }

  object Sheep extends AnimalBase {
    val name = "sheep"
    val entityClass = classOf[EntitySheep]
    val breedingFood = "wheat"
  }

  object Cow extends AnimalBase {
    val name = "cow"
    val entityClass = classOf[EntityCow]
    val breedingFood = "wheat"
  }

  object Squid extends AnimalBase {
    val name = "squid"
    val entityClass = classOf[EntitySquid]
    val breedingFood = "meatRaw"
  }

  type EntityClass = Class[_ <: EntityLivingBase]

  private val entityTypes = mutable.Set[AnimalBase]()

  addEntity(Pig)
  addEntity(Sheep)
  addEntity(Cow)
  addEntity(Squid)

  def infoForSubtype(subtype: String): Option[AnimalBase] = {
    entityTypes.find(info => info.name == subtype)
  }

  def infoForEntity(entity: EntityLivingBase): Option[AnimalBase] = {
    entityTypes.find(info => info.entityClass == entity.getClass)
  }

  def infoForStack(stack: ItemStack) = {
    infoForSubtype(getAnimalType(stack))
  }

  def addEntity(info: AnimalBase) {
    entityTypes.add(info)
  }

  def canUseOnEntity(player: EntityPlayer, entity: EntityLivingBase): Boolean = {
    if(entity.isChild)
      return false
    infoForEntity(entity).isDefined
  }

  def createForEntity(player: EntityPlayer, entity: EntityLivingBase): ItemStack = {
    val stack = new ItemStack(Items.emancipatedAnimal)
    Items.emancipatedAnimal.onCreateFromEntity(stack, entity, player)
    stack
  }

  def damageStack(stack: ItemStack, amount: Int) {
    stack.setItemDamage(stack.getItemDamage + amount)
    if(stack.getItemDamage >= stack.getMaxDamage) {
      stack.stackSize -= 1
    }
  }

  def makeChild(parent1: ItemStack, parent2: ItemStack): ItemStack = {
    damageStack(parent1, 1)
    damageStack(parent2, 1)
    val stack = new ItemStack(Items.emancipatedAnimal)
    Items.emancipatedAnimal.onCreateSubtype(stack, getAnimalType(parent1), true)
    stack.setItemDamage(stack.getMaxDamage)
    stack
  }

  def breedingFood(itemStack: ItemStack): String = {
    val info = EmancipatedAnimal.infoForSubtype(getAnimalType(itemStack))
    if(info.isEmpty) throw new Exception("no breeding food defined")
    info.get.breedingFood
  }

  def getAnimalType(stack: ItemStack): String = {
    if(stack.getTagCompound != null) {
      val name = stack.getTagCompound.getString("entityName")
      if(name != "") return name
    }
    return "invalid"
  }
}
