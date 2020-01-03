package dev.nomadblacky.www.website

import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom._

object WebAppMain {
  def main(args: Array[String]): Unit = {
    println("Hi")

    <.h1("www.nomadblacky.dev").renderIntoDOM(document.body)
  }
}
