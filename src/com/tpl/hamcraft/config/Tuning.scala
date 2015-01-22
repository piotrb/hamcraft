package com.tpl.hamcraft.config

import net.bdew.lib.recipes.gencfg._
import net.bdew.lib.recipes.{RecipeLoader, RecipeParser}
import java.io.{InputStreamReader, FileReader, File}
import com.tpl.hamcraft.HamCraftMod
import net.bdew.lib.recipes.gencfg.ConfigSection

object Tuning extends ConfigSection

object TuningLoader {

  abstract class EntryModifier extends CfgEntry

  case class EntryModifierAdd(v: Float) extends EntryModifier

  case class EntryModifierSub(v: Float) extends EntryModifier

  case class EntryModifierMul(v: Float) extends EntryModifier

  case class EntryModifierDiv(v: Float) extends EntryModifier

  class Parser extends RecipeParser with GenericConfigParser {

    def charWithCount = recipeChar ~ ("*" ~> int).? ^^ {
      case ch ~ cnt => (ch, cnt.getOrElse(1))
    }

    override def cfgStatement = cfgAdd | cfgMul | cfgSub | cfgDiv | super.cfgStatement

    def cfgAdd = ident ~ ("+" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierAdd(n.toFloat)) }
    def cfgMul = ident ~ ("*" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierMul(n.toFloat)) }
    def cfgSub = ident ~ ("-" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierSub(n.toFloat)) }
    def cfgDiv = ident ~ ("/" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierDiv(n.toFloat)) }
  }

  class Loader extends RecipeLoader with GenericConfigLoader {
    val cfgStore = Tuning

    override def newParser(): RecipeParser = new Parser()

  }

  val loader = new Loader

  def loadDelayed() = loader.processDelayedStatements()

  def load(part: String, checkJar: Boolean = true) {
    val f = new File(HamCraftMod.configDir, "%s-%s.cfg".format(HamCraftMod.modId, part))
    val r = if (f.exists() && f.canRead) {
      HamCraftMod.logInfo("Loading configuration from %s", f.toString)
      new FileReader(f)
    } else if (checkJar) {
      val res = "/assets/%s/%s-%s.cfg".format(HamCraftMod.modId, HamCraftMod.modId, part)
      val stream = this.getClass.getResourceAsStream(res)
      HamCraftMod.logInfo("Loading configuration from JAR - %s", this.getClass.getResource(res))
      new InputStreamReader(this.getClass.getResourceAsStream("/assets/%s/%s-%s.cfg".format(HamCraftMod.modId, HamCraftMod.modId, part)))
    } else {
      return
    }
    try {
      loader.load(r)
    } finally {
      r.close()
    }
  }
}
