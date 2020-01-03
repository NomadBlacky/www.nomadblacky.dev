package dev.nomadblacky.www.website.pages

import japgolly.scalajs.react.component.Scala.{Component, Unmounted}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{CtorType, _}
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

object HomePage {
  object Style extends StyleSheet.Inline {
    import dsl._
    val content: StyleA = style(minHeight(450.px), paddingTop(1.em), paddingLeft(1.em))
  }

  val component: Component[Unit, Unit, Unit, CtorType.Nullary] =
    ScalaComponent.builder
      .static("HomePage")(
        <.div(
          Style.content,
          <.p(<.a(^.href := "https://github.com/NomadBlacky", "GitHub")),
          <.p(<.a(^.href := "https://twitter.com/blac_k_ey", "Twitter")),
          <.p(<.a(^.href := "https://nomadblacky.hatenablog.com", "Blog"))
        )
      )
      .build

  def apply(): Unmounted[Unit, Unit, Unit] = component()
}
