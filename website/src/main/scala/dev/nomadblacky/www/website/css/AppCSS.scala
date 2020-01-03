package dev.nomadblacky.www.website.css

import dev.nomadblacky.www.website.components.TopNav
import dev.nomadblacky.www.website.pages.{HomePage, ItemsPage}
import scalacss.DevDefaults._
import scalacss.internal.mutable.GlobalRegistry

object AppCSS {
  def load(): Unit = {
    GlobalRegistry.register(GlobalStyle, HomePage.Style, ItemsPage.Style, TopNav.Style)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}

object GlobalStyle extends StyleSheet.Inline {
  import dsl._
  style(
    unsafeRoot("body")(
      margin.`0`,
      padding.`0`,
//      fontSize(14.px),
      fontFamily := "Roboto, sans-serif",
      backgroundColor(c"rgb(95, 95, 109)"),
      color(c"rgb(225, 225, 225)")
    ),
    unsafeRoot("a:link")(
      color := "#fffc36"
    ),
    unsafeRoot("a:visited")(
      color := "#ff7474"
    )
  )
}
