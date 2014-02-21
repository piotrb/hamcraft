package com.tpl.hamcraft.machines.breeder

import net.minecraft.util.Icon
import com.tpl.hamcraft.config.Machines
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister
import com.tpl.hamcraft.machines.BlockBase
import net.bdew.lib.rotate.IconType

class BlockBreeder(id: Int) extends BlockBase[TileBreeder](classOf[TileBreeder], id, "breeder") {
  lazy val guiId: Int = Machines.breeder.guiId
}
