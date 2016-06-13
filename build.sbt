//Include sbt-assembly-0.17.jar in the Dependencies
import sbt.Keys._
import sbt._
//import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport._


version := "0.1-SNAPSHOT"
organization := "com.databricks"
scalaVersion := "2.10.5"
name := "spark-cassandra-guava-shade"

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0-M2"
)

// There is a conflict between Guava versions on Cassandra Drive and Spark
// Shading Guava Package
assemblyShadeRules in assembly := Seq(ShadeRule.rename("com.google.**" -> "shadeio.@1").inAll)

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}
