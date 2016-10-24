name := "test_scala_sensor_processing"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0",
  "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.0",
  "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.0",
  "joda-time" % "joda-time" % "2.9.4",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "io.spray" %% "spray-json" % "1.3.2",
  "net.virtual-void" %%  "json-lenses" % "0.6.1"
)
