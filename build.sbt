ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "dev.nomadblacky"
ThisBuild / organizationName := "NomadBlacky"
ThisBuild / version := "0.1.0-SNAPSHOT"

val versions = new {
  val cdk          = "1.19.0"
  val scalaJSReact = "1.5.0-RC2"
  val scalaCSS     = "0.6.0-RC1"
  val reactJS      = "16.7.0"
}

lazy val infra = (project in file("infra"))
  .settings(
    name := "infra",
    libraryDependencies ++= Seq(
        "software.amazon.awscdk" % "cloudfront"      % versions.cdk,
        "software.amazon.awscdk" % "route53-targets" % versions.cdk,
        "software.amazon.awscdk" % "s3-deployment"   % versions.cdk
      )
  )

val generatedDir = file("public/js")
lazy val website = (project in file("website"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "website",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
        "org.scala-js"                      %%% "scalajs-dom" % "0.9.8",
        "com.github.japgolly.scalajs-react" %%% "core"        % versions.scalaJSReact,
        "com.github.japgolly.scalajs-react" %%% "extra"       % versions.scalaJSReact,
        "com.github.japgolly.scalacss"      %%% "core"        % versions.scalaCSS,
        "com.github.japgolly.scalacss"      %%% "ext-react"   % versions.scalaCSS
      ),
    jsDependencies ++= Seq(
        "org.webjars.npm" % "react"     % versions.reactJS / "umd/react.development.js" minified "umd/react.production.min.js" commonJSName "React",
        "org.webjars.npm" % "react-dom" % versions.reactJS / "umd/react-dom.development.js" minified "umd/react-dom.production.min.js" dependsOn "umd/react.development.js" commonJSName "ReactDOM",
        "org.webjars.npm" % "react-dom" % versions.reactJS / "umd/react-dom-server.browser.development.js" minified "umd/react-dom-server.browser.production.min.js" dependsOn "umd/react-dom.development.js" commonJSName "ReactDOMServer"
      ),
    crossTarget in (Compile, fullOptJS) := generatedDir,
    crossTarget in (Compile, fastOptJS) := generatedDir,
    crossTarget in (Compile, packageJSDependencies) := generatedDir,
    crossTarget in (Compile, packageMinifiedJSDependencies) := generatedDir,
    artifactPath in (Compile, fastOptJS) := (crossTarget in (Compile, fastOptJS)).value / ((moduleName in fastOptJS).value + "-opt.js")
  )
