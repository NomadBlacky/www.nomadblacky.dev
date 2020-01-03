package dev.nomadblacky.www.website.components

import dev.nomadblacky.www.website.model.Menu
import dev.nomadblacky.www.website.routes.AppRouter.{AppPage, Home}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Reusability, ScalaComponent}
import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

object TopNav {

  object Style extends StyleSheet.Inline {
    import dsl._

    val navMenu =
      style(
        display.flex,
        alignItems.center,
        backgroundColor(c"rgb(60, 60, 60)"),
        margin.`0`,
        listStyle := "none",
        paddingInlineStart.`0`
      )

    val icon = style(padding(0.5.em), borderRadius(50.%%), width(60.px), cursor.pointer)

    val title = style(padding(20.px), fontSize(1.5.em), fontWeight.bold, color(c"rgb(255, 255, 80)"), cursor.pointer)

    val menuItem = styleF.bool { selected =>
      styleS(
        padding(20.px),
        fontSize(1.3.em),
        cursor.pointer,
        color(c"rgb(244, 233, 233)"),
        mixinIfElse(selected)(backgroundColor(c"#b9322e"), fontWeight._500)(&.hover(backgroundColor(c"#B6413E")))
      )
    }
  }

  final case class Props(menus: Vector[Menu], selectedPage: AppPage, ctrl: RouterCtl[AppPage])

  implicit val currentPageReuse = Reusability.by_==[AppPage]
  implicit val propsReuse       = Reusability.by((_: Props).selectedPage)

  val component = ScalaComponent
    .builder[Props]("TopNav")
    .render_P { P =>
      <.header(
        <.nav(
          <.ul(
            Style.navMenu,
            <.li(
              P.ctrl setOnClick Home,
              <.img(Style.icon, ^.src := "https://s.gravatar.com/avatar/917c89046a8ba98f398ea16b7b335779?s=80")
            ),
            <.li(Style.title, "www.nomadblacky.dev", P.ctrl setOnClick Home),
            P.menus.toTagMod { menu =>
              <.li(
                ^.key := menu.name,
                Style.menuItem(menu.page.getClass == P.selectedPage.getClass),
                menu.name,
                P.ctrl setOnClick menu.page
              )
            }
          )
        )
      )
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  def apply(props: Props) = component(props)
}
