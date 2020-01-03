package dev.nomadblacky.www.website.routes

import dev.nomadblacky.www.website.components.TopNav
import dev.nomadblacky.www.website.components.items.AboutItem
import dev.nomadblacky.www.website.model.Menu
import dev.nomadblacky.www.website.pages.HomePage
import japgolly.scalajs.react.extra.router.{
  BaseUrl,
  Redirect,
  Resolution,
  Router,
  RouterConfig,
  RouterConfigDsl,
  RouterCtl
}
import japgolly.scalajs.react.vdom.html_<^.<

object AppRouter {

  sealed trait AppPage
  case object Home          extends AppPage
  case class Items(p: Item) extends AppPage

  val config: RouterConfig[AppPage] = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._
    val itemRoutes: Rule =
      Item.routes.prefixPath_/("#items").pmap[AppPage](Items) {
        case Items(p) => p
      }
    (trimSlashes
    | staticRoute(root, Home) ~> render(HomePage())
    | itemRoutes)
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  val mainMenu = Vector(
    Menu("About", Items(Item.About))
  )

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) =
    <.div(
      TopNav(TopNav.Props(mainMenu, r.page, c)),
      r.render()
    )

  val baseUrl = BaseUrl.until_#

  val router = Router(baseUrl, config)
}
