package com.rediff.tests.stocks

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Login.{login, validateLogin}
import com.rediff.tests.common.Portfolio.selectPortfolio
import com.rediff.tests.common.Reporting._
import com.rediff.tests.common.Stock.{addStock, chooseAction, findCurrentStockQuantity, goToBuySell, modifyStock, verifyStockQuantity, verifyTransactionHistory}
import com.rediff.tests.common.UserInfo

class StockTests extends BaseTest{

  val portfolioName = "Cat"
  val companyName = "Reliance Industries Ltd."
  val date = "21-12-2019"
  val quantity = 100
  val price = 200
  val action = "Buy"


  " A user " should " be able to add new stock to his portfolio  " in {
    initiateLogs()
    login(UserInfo.username, UserInfo.password)(webDriver)
    validateLogin("portfolio")(webDriver)
    selectPortfolio(portfolioName)(webDriver)
    val currentQuantity = findCurrentStockQuantity(companyName)(webDriver)
    log("The current stock quantity is "+currentQuantity)
    addStock(companyName,date,quantity,price)(webDriver)
    verifyStockQuantity(action,companyName,quantity,currentQuantity)(webDriver)
    verifyTransactionHistory(action,companyName,quantity)(webDriver)
  }


  " A user " should " be able to modify existing stock in  his portfolio  " in {
    initiateLogs()
    val action = "Sell"
    login(UserInfo.username, UserInfo.password)(webDriver)
    validateLogin("portfolio")(webDriver)
    selectPortfolio(portfolioName)(webDriver)
    val currentQuantity = findCurrentStockQuantity(companyName)(webDriver)
    log("The current stock quantity is "+currentQuantity)
    goToBuySell(companyName)(webDriver)
    chooseAction(companyName,action)(webDriver)
    modifyStock(date,quantity,price)(webDriver)
    verifyStockQuantity(action,companyName,quantity,currentQuantity)(webDriver)
    verifyTransactionHistory(action,companyName,quantity)(webDriver)
  }
}

