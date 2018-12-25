name := "shoppingCart"

version := "0.1"

scalaVersion := "2.12.8"

assemblyJarName in assembly := "shoppingCart.jar"

libraryDependencies ++= Seq(
  "org.scalamock" %% "scalamock" % "4.1.0" % "test",
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
)