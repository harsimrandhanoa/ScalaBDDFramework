package com.rediff.tests.portfolio

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Login.{login, validateLogin}
import com.rediff.tests.common.Portfolio._
import com.rediff.tests.common.UserInfo
import org.scalatest.CancelAfterFailure

class PortfolioTests extends BaseTest with  CancelAfterFailure{

  val r = new scala.util.Random()
  val randomNumber: Int = r.nextInt(1000)
  val portfolioName: String = "New portfolio "+randomNumber

  " A user " should " be able to add a new portfolio to his account " in {
    initiateLogs()
    login( UserInfo.username,UserInfo.password)(webDriver)
    validateLogin("portfolio")(webDriver)
    addPortfolio(portfolioName)(webDriver)
    verifyPortfolioIsAdded(portfolioName)(webDriver)
  }

  " A user " should " be able to delete a existing  portfolio from an account " in {
    initiateLogs()
    login( UserInfo.username,UserInfo.password)(webDriver)
    validateLogin("portfolio")(webDriver)
    selectPortfolio(portfolioName)(webDriver)
    deletePortfolio(portfolioName)(webDriver)
    verifyPortfolioIsDeleted(portfolioName)(webDriver)
  }

}

