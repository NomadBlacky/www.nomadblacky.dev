ThisBuild / scalaVersion := "2.13.1"
ThisBuild / organization := "dev.nomadblacky"
ThisBuild / organizationName := "NomadBlacky"
ThisBuild / version := "0.1.0-SNAPSHOT"

val versions = new {
  val cdk = "1.19.0"
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

lazy val website = (project in file("website"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "website",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.8"
      ),
    crossTarget in (Compile, fullOptJS) := file("public/js"),
    crossTarget in (Compile, fastOptJS) := file("public/js"),
    artifactPath in (Compile, fastOptJS) := (crossTarget in (Compile, fastOptJS)).value / ((moduleName in fastOptJS).value + "-opt.js")
  )
