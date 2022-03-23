package com.rediff.tests.common

import com.rediff.tests.base.BaseTest

object WebLocators extends BaseTest{

  val rediffMail: WebLocators.CssSelectorQuery = cssSelector(".mailicon")
  val signinLink= xpath("//a[@class='signin']")
  val username = cssSelector("#useremail")
  val password = cssSelector("#userpass")
  val loginButton= cssSelector("#loginsubmit")
  val crmLink = xpath("//*[@id='zl-myapps']/div[1]/div[2]/div/a/div")
  val createPortfolio = cssSelector("#createPortfolio")
  val portfolioName = cssSelector("#create")
  val createPortfolioButton = cssSelector("input#createPortfolioButton")
  val portfolioDropdown = cssSelector("#portfolioid")
  val deletePortfolio = cssSelector("#deletePortfolio")
  val addStock = cssSelector("#addStock")
  val addStockName = cssSelector("input#addstockname")
  val stockPurchaseDate = cssSelector("#stockPurchaseDate")
  val monthYear = cssSelector("div.dpTitleText")
  val dateBackButton = xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[2]/button")
  val addStockQty = cssSelector("#addstockqty")
  val addStockPrice = cssSelector("#addstockprice")
  val addStockButton = cssSelector("#addStockButton")
  val stockTable = cssSelector("table#stock > tbody")
  val equityAction = cssSelector("#equityaction")
  val buySellCalendar = cssSelector("#buySellCalendar")
  val buySellQty = cssSelector("#buysellqty")
  val buySellPrice = cssSelector("#buysellprice")
  val buySellStockButton = cssSelector("#buySellStockButton")
  val latestShareChangeQuantity = xpath("//table[@class='dataTable']/tbody/tr[1]/td[3]")
}
