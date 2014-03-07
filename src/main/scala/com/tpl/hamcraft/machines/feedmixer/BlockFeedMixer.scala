package com.tpl.hamcraft.machines.feedmixer

import com.tpl.hamcraft.machines.BlockBase
import com.tpl.hamcraft.config.Machines

class BlockFeedMixer(id: Int) extends BlockBase[TileFeedMixer](classOf[TileFeedMixer], id, "feedmixer") {
  lazy val guiId: Int = Machines.feedmixer.guiId
}
