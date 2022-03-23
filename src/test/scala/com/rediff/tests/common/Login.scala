package com.rediff.tests.common

import com.rediff.tests.Conf.Conf
import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Reporting._
import com.rediff.tests.common.Util._
import org.openqa.selenium.WebDriver
import org.scalatest.time.{Seconds, Span}


object Login extends BaseTest{

  val e: WebLocators.type = WebLocators

  def login(email: String, password: String)(implicit webDriver : WebDriver): Unit = {
    log("Logging into app with email "+email +" password "+password)
    go to Conf.host
    typeText(e.username.webElement,email)
    typeText(e.password.webElement,password)
    clickElement(e.loginButton.webElement)
    Thread.sleep(5000)
  }

  def validateLogin(urlEnd:String)(implicit webDriver : WebDriver): Unit = {
    try {
      eventually(timeout(Span(5, Seconds)), interval(Span(1, Seconds))) {
        getCurrentUrl() should endWith (urlEnd)}}
    catch {
      case _:Exception => logFailure("Url "+getCurrentUrl()(webDriver) + " did not end with  "+urlEnd)
    }
  }
}
