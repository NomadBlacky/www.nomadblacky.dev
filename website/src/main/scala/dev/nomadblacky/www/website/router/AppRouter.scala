package dev.nomadblacky.www.website.router

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
  case object Home extends AppPage

  val config: RouterConfig[AppPage] = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._
    (trimSlashes | staticRoute(root, Home) ~> render(HomePage()))
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) =
    <.div(
//      TopNav(TopNav.Props(mainMenu, r.page, c)),
      r.render()
    )

  val baseUrl = BaseUrl.until_#

  val router = Router(baseUrl, config)
}
