package com.tpl.hamcraft.nei

import net.bdew.lib.Misc
import codechicken.nei.recipe.GuiCraftingRecipe

object NEIRecipeProxy {
  val hasNei = Misc.haveModVersion("NotEnoughItems")

  def openRecipes(id: String) {
    if (hasNei)
      GuiCraftingRecipe.openRecipeGui(id)
  }
}
