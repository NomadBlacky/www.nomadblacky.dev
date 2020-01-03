package dev.nomadblacky.www.website.components.items

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object AboutItem {

  val component = ScalaComponent.builder
    .static("About")(<.div("Under construction..."))
    .build

  def apply() = component().vdomElement
}
