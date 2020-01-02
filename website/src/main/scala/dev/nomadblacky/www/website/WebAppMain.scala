package dev.nomadblacky.www.website

import org.scalajs.dom
import dom.document

object WebAppMain {
  def main(args: Array[String]): Unit = {
    println("Hi")

    val h1 = document.createElement("h1")
    h1.innerText = "www.nomadblacky.dev"

    document.body.appendChild(h1)
  }
}
