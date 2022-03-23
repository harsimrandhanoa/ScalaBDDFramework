name := "ScalabddProject"

version := "0.1"

scalaVersion := "2.13.8"
Test / parallelExecution := false


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies+="org.seleniumhq.selenium" % "selenium-java" % "3.141.59" % "test"
libraryDependencies+="com.typesafe" % "config" % "1.3.1"
libraryDependencies += "com.aventstack" % "extentreports" % "5.0.0"
libraryDependencies  += "org.scalatestplus" %% "selenium-3-141" % "3.2.9.0" % "test"
libraryDependencies += "org.seleniumhq.selenium" % "selenium-support" % "3.12.0"