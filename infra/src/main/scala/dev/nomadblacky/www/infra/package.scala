package dev.nomadblacky.www

import java.util

package object infra {
  def list[A](as: A*): util.List[A] = util.Arrays.asList(as: _*)
}
