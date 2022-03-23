package com.rediff.tests.Conf

import com.typesafe.config.{Config, ConfigFactory}


object Conf {

  protected val configName: String = Option(System.getProperty("test.conf")).getOrElse("test")

  lazy protected val conf: Config = ConfigFactory.load(configName)

  lazy val host: String = conf.getString("rediff-tests.host")


}

