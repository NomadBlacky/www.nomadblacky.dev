package dev.nomadblacky.www.website

import dev.nomadblacky.www.website.css.AppCSS
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom._

object WebAppMain {
  def main(args: Array[String]): Unit = {
    println("Hi")

    AppCSS.load()

    <.h1("www.nomadblacky.dev").renderIntoDOM(document.getElementById("app"))
  }
}
