package com.tpl.hamcraft.machines.feedmixer

import com.tpl.hamcraft.machines.BlockBase

object BlockFeedMixer extends BlockBase[TileFeedMixer](classOf[TileFeedMixer], "feedmixer") {
  lazy val guiId: Int = MachineFeedMixer.guiId
}
