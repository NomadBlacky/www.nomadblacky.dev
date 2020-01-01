package dev.nomadblacky.www.infra

import software.amazon.awscdk.core.{App, Construct, Stack}

object CDKMain {
  def main(args: Array[String]): Unit = {
    val app = new App()

    new WWWStack(app, "WWWStack")

    app.synth()
  }
}

class WWWStack(scope: Construct, id: String) extends Stack(scope, id) {}
