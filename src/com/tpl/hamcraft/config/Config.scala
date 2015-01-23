package com.tpl.hamcraft.config

import net.bdew.lib.gui.GuiHandler

object Config {
  val guiHandler = new GuiHandler

  def load() {
    Fluids.load()
    Blocks.load()
    Items.load()
    Machines.load()
  }
}
