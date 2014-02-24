package com.tpl.hamcraft.machines.milker

import com.tpl.hamcraft.config.Machines
import com.tpl.hamcraft.machines.BlockBase

class BlockMilker(id: Int) extends BlockBase[TileMilker](classOf[TileMilker], id, "milker") {
  lazy val guiId: Int = Machines.milker.guiId
}
