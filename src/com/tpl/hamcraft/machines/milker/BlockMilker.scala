package com.tpl.hamcraft.machines.milker

import com.tpl.hamcraft.machines.BlockBase

object BlockMilker extends BlockBase[TileMilker](classOf[TileMilker], "milker") {
  lazy val guiId: Int = MachineMilker.guiId
}
