package dev.nomadblacky.www.website.routes

import dev.nomadblacky.www.website.components.items.AboutItem
import dev.nomadblacky.www.website.pages.ItemsPage
import japgolly.scalajs.react.extra.router.RouterConfigDsl
import japgolly.scalajs.react.vdom.VdomElement

sealed abstract class Item(val title: String, val routerPath: String, val render: () => VdomElement)

object Item {

  case object About extends Item("About", "about", () => AboutItem())

  val menu: Vector[Item] = Vector(About)

  val routes = RouterConfigDsl[Item].buildRule { dsl =>
    import dsl._
    menu
      .map { i =>
        staticRoute(i.routerPath, i) ~> renderR(r => ItemsPage(ItemsPage.Props(i, r)))
      }
      .reduce(_ | _)
  }
}
