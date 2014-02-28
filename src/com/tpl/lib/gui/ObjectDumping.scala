package com.tpl.lib.gui

trait ObjectDumping {
  protected def dump[T](t: T)(implicit mt: Manifest[T] = null) = "%s: %s".format(t,if (mt == null) "<?>" else mt.toString)
}
