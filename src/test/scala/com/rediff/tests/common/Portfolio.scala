package com.rediff.tests.common

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Util.{clickElement,typeText,clear,getFirstValueFromDropdown
  ,acceptAlert,selectValueFromDropDown,waitForElementToBeIntractable,waitForPageToLoad}
import org.openqa.selenium.WebDriver
import com.rediff.tests.common.Reporting._

object Portfolio extends BaseTest{

  val e: WebLocators.type = WebLocators

  def addPortfolio(portfolioName:String)(implicit webDriver : WebDriver): Unit = {
    waitForPageToLoad()
    log("Creating Portfolio named "+portfolioName)
    clickElement(e.createPortfolio.webElement)
    clear(e.portfolioName.webElement)
    typeText(e.portfolioName.webElement,portfolioName)
    clickElement(e.createPortfolioButton.webElement)
  }

  def verifyPortfolioIsAdded(portfolioName:String)(implicit webDriver : WebDriver): Unit = {
    log("Verifying Portfolio named "+portfolioName)
    waitForPageToLoad()
    waitForElementToBeIntractable(e.portfolioDropdown.webElement)
    val valueInDropDown =  getFirstValueFromDropdown(e.portfolioDropdown.webElement)
    if(valueInDropDown.equals(portfolioName)){
      logPass("Portfolio " + portfolioName + " has been added")}
    else {
      logFailure("portfolio to be added is "+portfolioName + " but the one added is "+valueInDropDown)
    }
  }

  def selectPortfolio(portfolioName:String)(implicit webDriver : WebDriver): Unit = {
    log("About to select portfolio named "+portfolioName)
    waitForPageToLoad()
    waitForElementToBeIntractable(e.portfolioDropdown.webElement)
    selectValueFromDropDown(e.portfolioDropdown.webElement,portfolioName)
  }

  def deletePortfolio(portfolioName:String)(implicit webDriver : WebDriver): Unit = {
    log("About to delete portfolio "+portfolioName)
    waitForPageToLoad()
    waitForElementToBeIntractable(e.deletePortfolio.webElement)
    clickElement(e.deletePortfolio.webElement)(webDriver)
    acceptAlert
  }

  def verifyPortfolioIsDeleted(portfolioName:String)(implicit webDriver : WebDriver): Unit = {
    log("Verifying portfolio is deleted "+portfolioName)
    waitForPageToLoad()
    waitForElementToBeIntractable(e.portfolioDropdown.webElement)
    val valueInDropDown =  getFirstValueFromDropdown(e.portfolioDropdown.webElement)
    if(!valueInDropDown.equals(portfolioName)){
      logPass("First value is "+valueInDropDown+ " and its not "+portfolioName +" so that value must be deleted")}
    else {
      logFailure("Portfolio "+portfolioName + "should have been  deleted but the its still there in dropdown "+valueInDropDown)
    }
  }




}
