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
import com.tpl.hamcraft.config.{Tuning, Items}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.nbt
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.Icon
import scala.collection.mutable

class EmancipatedAnimal(id: Int) extends Item(id) {

  @SideOnly(Side.CLIENT)
  var subTypeIcons: mutable.Map[String, Icon] = mutable.Map.empty[String, Icon]

  setUnlocalizedName(HamCraftMod.modId + ".emancipatedanimal")

  setMaxStackSize(1)
  setMaxDamage(10)

  setHasSubtypes(true)

  def getAnimalType(stack: ItemStack) = EmancipatedAnimal.getAnimalType(stack)
  def infoForSubtype(name: String) = EmancipatedAnimal.infoForSubtype(name)
  def infoForEntity(entity: EntityLivingBase) = EmancipatedAnimal.infoForEntity(entity)
  def isChild(stack: ItemStack) = EmancipatedAnimal.isChild(stack)
  def entityTypes = EmancipatedAnimal.entityTypes
  def cfg = EmancipatedAnimal.cfg

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
    var subtype = info match {
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
    super.getUnlocalizedName + "." + getAnimalType(stack)
  }

  override def getSubItems(id: Int, tab: CreativeTabs, list: util.List[_]) = {
    val l = list.asInstanceOf[java.util.List[ItemStack]]
    entityTypes.foreach( info => {
      val stack = new ItemStack(id, 1, 0)
      onCreateSubtype(stack, info.name, child = false)
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

class AnimalInfo(val name: String, val entityClass: Class[_ <: EntityLivingBase]) {
  lazy val cfg = EmancipatedAnimal.cfg.getSection(name)
  lazy val breedingDamage = cfg.getInt("breedingDamage")
  lazy val breedingFood = cfg.getString("breedingFood")
}

object EmancipatedAnimal {

  lazy val cfg = Tuning.getSection("Items").getSection("EmancipatedAnimal")

  private val entityTypes = mutable.Set[AnimalInfo]()

  addEntity("chicken", classOf[EntityChicken])
  addEntity("pig",     classOf[EntityPig])
  addEntity("sheep",   classOf[EntitySheep])
  addEntity("cow",     classOf[EntityCow])
  addEntity("squid",   classOf[EntitySquid])

  def infoForSubtype(subtype: String): Option[AnimalInfo] = {
    entityTypes.find(info => info.name == subtype)
  }

  def infoForEntity(entity: EntityLivingBase): Option[AnimalInfo] = {
    entityTypes.find(info => info.entityClass == entity.getClass)
  }

  def infoForStack(stack: ItemStack) = {
    infoForSubtype(getAnimalType(stack))
  }

  def addEntity(name: String, entityClass: Class[_ <: EntityLivingBase]) {
    entityTypes.add(new AnimalInfo(name, entityClass))
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
    damageStack(parent1, infoForStack(parent1).get.breedingDamage)
    damageStack(parent2, infoForStack(parent2).get.breedingDamage)
    val stack = new ItemStack(Items.emancipatedAnimal)
    Items.emancipatedAnimal.onCreateSubtype(stack, getAnimalType(parent1), child = true)
    stack.setItemDamage(stack.getMaxDamage)
    stack
  }

  def growUp(child: ItemStack) {
    child.stackTagCompound.setBoolean("child", false)
  }


  def isChild(stack: ItemStack): Boolean = {
    if(stack.getTagCompound != null) {
      return stack.stackTagCompound.getBoolean("child")
    }
    false
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
    "invalid"
  }
}
