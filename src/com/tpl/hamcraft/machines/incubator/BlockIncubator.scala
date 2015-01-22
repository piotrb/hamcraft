package com.tpl.hamcraft.machines.incubator

import com.tpl.hamcraft.config.Machines
import com.tpl.hamcraft.machines.BlockBase

class BlockIncubator(id: Int) extends BlockBase[TileIncubator](classOf[TileIncubator], id, "incubator") {
  lazy val guiId: Int = Machines.incubator.guiId
}
