name := "akka-2.6-M4-remoting-issue"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster-typed" % "2.6-20190712-192311",
  "org.scalatest"     %% "scalatest"          % "3.0.8"
)

resolvers += "Akka-Snapshots" at "https://repo.akka.io/snapshots"
