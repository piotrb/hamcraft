package com.tpl.hamcraft.config

import net.bdew.lib.config.IdManager
import net.bdew.lib.gui.GuiHandler
import java.io.File
import net.minecraftforge.common.Configuration

object Config {
  var IDs: IdManager = null
  val guiHandler = new GuiHandler

  var powerShowUnits = "RF"
  var powerShowMultiplier = 1F

  def load(cfg: File) {
    val c = new Configuration(cfg)
    c.load()

    try {
      powerShowUnits = "RF"
      powerShowMultiplier = Tuning.getSection("Power").getFloat(powerShowUnits + "_MJ_Ratio")
      IDs = new IdManager(c, 16000, 3600)
      Fluids.load()
      Blocks.load()
      Items.load()
      Machines.load()
    } finally {
      c.save()
    }

  }
}