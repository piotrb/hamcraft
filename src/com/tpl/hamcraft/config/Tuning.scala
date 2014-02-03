package com.tpl.hamcraft.config

import net.bdew.lib.recipes.gencfg._
import net.bdew.lib.recipes.{RecipeLoader, DelayedStatement, RecipeParser}
import cpw.mods.fml.common.Loader
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

//  case class StMutagen(st: StackRef, mb: Int) extends DelayedStatement

//  case class StAssembly(rec: List[(Char, Int)], power: Int, result: StackRef, cnt: Int) extends CraftingStatement

  class Parser extends RecipeParser with GenericConfigParser {
//    override def statement = mutagen | assembly | super.statement
//    def mutagen = "mutagen" ~> ":" ~> spec ~ ("->" ~> int) ^^ {
//      case sp ~ n => StMutagen(sp, n)
//    }

    def charWithCount = recipeChar ~ ("*" ~> int).? ^^ {
      case ch ~ cnt => (ch, cnt.getOrElse(1))
    }

//    def assembly = "assembly" ~> ":" ~> (charWithCount <~ ",").+ ~ (int <~ "mj") ~ ("=>" ~> specWithCount) ^^ {
//      case r ~ p ~ (s ~ n) => StAssembly(r, p, s, n.getOrElse(1))
//    }
//
    override def cfgStatement = cfgAdd | cfgMul | cfgSub | cfgDiv | super.cfgStatement

    def cfgAdd = ident ~ ("+" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierAdd(n.toFloat)) }
    def cfgMul = ident ~ ("*" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierMul(n.toFloat)) }
    def cfgSub = ident ~ ("-" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierSub(n.toFloat)) }
    def cfgDiv = ident ~ ("/" ~> "=" ~> decimalNumber) ^^ { case id ~ n => CfgVal(id, EntryModifierDiv(n.toFloat)) }
  }

  class Loader extends RecipeLoader with GenericConfigLoader {
    val cfgStore = Tuning

    override def newParser(): RecipeParser = new Parser()

    override def processDelayedStatement(s: DelayedStatement): Unit = s match {
//      case StMutagen(st, mb) =>
//        val in = getConcreteStack(st)
//        MutagenRegistry.register(in, mb)
//        log.info("Added mutagen source %s -> %d mb".format(in, mb))
//
//      case StAssembly(rec, power, out, cnt) =>
//        log.info("Adding assembly recipe: %s + %d mj => %s * %d".format(rec, power, out, cnt))
//        val outStack = getConcreteStack(out, cnt)
//        val stacks = rec.map {
//          case (c, n) =>
//            val s = getConcreteStack(currCharMap(c), n)
//            if (s.getItemDamage == OreDictionary.WILDCARD_VALUE) {
//              s.setItemDamage(0)
//              log.warning("%s added with wildcard metadata which is unsupported, assuming 0".format(s))
//            }
//            log.info("%s -> %s".format(c, s))
//            s
//        }
//        log.info("Output: %s".format(outStack))
//        AssemblyRecipe.assemblyRecipes.add(new AssemblyRecipe(stacks.toArray, power, outStack))
//        log.info("Done")
        case _ => super.processDelayedStatement(s)
    }
  }

  val loader = new Loader

  def loadDealayed() = loader.processDelayedStatements()

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
