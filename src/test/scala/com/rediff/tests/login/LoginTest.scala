package com.rediff.tests.login

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Login._
import com.rediff.tests.common.Reporting._
import com.rediff.tests.common.UserInfo
import org.scalatest.AppendedClues


class LoginTest  extends BaseTest with AppendedClues {

  override def beforeAll():Unit = {}

  " A user that logs in " should "land on the home page" in {
    initiateLogs()
    login(UserInfo.username,UserInfo.password)(webDriver)
    validateLogin("portfolio")(webDriver)
    logPass("Login successful")
  }

  " A user that logs in with wrong password" should " not be able to land on the home page" in {
    initiateLogs()
    login(UserInfo.username,"123!@")(webDriver)
    validateLogin("portfolio-login")(webDriver)
    logPass("Login not successful because of the credentials we provided were not correct")
  }

}
