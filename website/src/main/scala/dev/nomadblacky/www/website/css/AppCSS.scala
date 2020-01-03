package dev.nomadblacky.www.website.css

import scalacss.DevDefaults._
import scalacss.internal.mutable.GlobalRegistry

object AppCSS {
  def load(): Unit = {
    GlobalRegistry.register(GlobalStyle)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}

object GlobalStyle extends StyleSheet.Inline {
  import dsl._
  style(
    unsafeRoot("body")(
      margin.`0`,
      padding.`0`,
      fontSize(14.px),
      fontFamily := "Roboto, sans-serif"
    )
  )
}
