package dev.nomadblacky.www.website

import dev.nomadblacky.www.website.css.AppCSS
import dev.nomadblacky.www.website.router.AppRouter
import org.scalajs.dom._

object WebAppMain {
  def main(args: Array[String]): Unit = {
    println("Hi")

    AppCSS.load()

    AppRouter.router().renderIntoDOM(document.getElementById("app"))
  }
}
