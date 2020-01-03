package dev.nomadblacky.www.website.pages

import japgolly.scalajs.react.component.Scala.{Component, Unmounted}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{CtorType, _}
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

object HomePage {
  object Style extends StyleSheet.Inline {
    import dsl._
    val content: StyleA = style(textAlign.center, fontSize(30.px), minHeight(450.px), paddingTop(40.px))
  }

  val component: Component[Unit, Unit, Unit, CtorType.Nullary] =
    ScalaComponent.builder
      .static("HomePage")(<.div(Style.content, <.h1("www.nomadblacky.dev")))
      .build

  def apply(): Unmounted[Unit, Unit, Unit] = component()
}
