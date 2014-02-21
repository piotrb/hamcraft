package com.tpl.hamcraft.machines.feedmixer

import com.tpl.hamcraft.machines.BlockBase
import net.minecraft.util.Icon
import com.tpl.hamcraft.config.Machines
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IconRegister
import net.bdew.lib.rotate.IconType

class BlockFeedMixer(id: Int) extends BlockBase[TileFeedMixer](classOf[TileFeedMixer], id, "feedmixer") {
  lazy val guiId: Int = Machines.feedmixer.guiId
}
