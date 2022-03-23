package com.rediff.tests.common

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Reporting.log
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver, WebElement}
import org.openqa.selenium.support.ui.{ExpectedConditions, Select, WebDriverWait}

import scala.util.control.Breaks._

object Util extends BaseTest{

  def getCurrentUrl()(implicit webDriver : WebDriver):String = {
    currentUrl
  }

  def typeText(webElement: WebElement,string: String)(implicit webDriver : WebDriver):Unit = {
    log("Typing text  "+string +" on web element "+webElement)
    webElement.sendKeys(string)
  }

  def clear(webElement: WebElement)(implicit webDriver : WebDriver):Unit = {
    log("Clearing "+webElement)
    webElement.clear()
  }
  def clickElement(webElement: WebElement)(implicit webDriver : WebDriver):Unit = {
    log("Clicking on element "+webElement)
    click on webElement
  }

  def waitForPageToLoad()(implicit webDriver:WebDriver): Unit = {
    val js = webDriver.asInstanceOf[JavascriptExecutor]
    // ajax status
    breakable {
      for (_ <- 0 to 10) {
        val state = js.executeScript("return document.readyState;").asInstanceOf[String]
        if (state == "complete")
          break //todo: break is not supported
        else Thread.sleep(2000)
      }
    }
    // check for jquery status
    breakable {
      for (_ <- 0 to 10) {
        val d = js.executeScript("return jQuery.active;").asInstanceOf[Long]
        if (d.longValue == 0)
          break //todo: break is not supported
        else Thread.sleep(2000)

      }
    }
  }

  def getFirstValueFromDropdown(webElement: WebElement): String = {
    log("Selecting first value from dropdown menu "+webElement)
    val s = new Select(webElement)
    s.getFirstSelectedOption.getText
  }

  def selectValueFromDropDown(webElement: WebElement, optionToBeSelected: String): Unit = {
    log("Selecting value  "+ optionToBeSelected + " from dropdown "+webElement)
    val s = new Select(webElement)
    s.selectByVisibleText(optionToBeSelected)
  }

  def acceptAlert()(implicit webDriver : WebDriver): Unit = {
    log("About to accept alert")
    val wait = new WebDriverWait(webDriver,5)
    wait.until(ExpectedConditions.alertIsPresent)

    try {
      webDriver.switchTo.alert.accept()
      webDriver.switchTo.defaultContent
      log("Alert accepted successfully")
    } catch {
      case _: Exception =>
        log("Alert not found when mandatory")
    }
  }

  def waitForElementToBeIntractable(webElement: WebElement)(implicit webDriver : WebDriver): Unit = {
    log("Waiting for web element   "+ webElement  + " using explicit wait ")
    val wait = new WebDriverWait(webDriver, 5)
    wait.until(ExpectedConditions.elementToBeClickable(webElement))
    Thread.sleep(1000)
  }

  def getRowNumWithCellData(table: WebElement, data: String)(implicit webDriver:WebDriver) : Int = {
    var shortFormData = data
    if (data == "Reliance Industries Ltd.") {
      shortFormData = "Reliance Inds."}

    log("Checking company in table " + data)
    val rows = table.findElements(By.tagName("tr"))
    for (rNum <- 0 until rows.size) {
      val row = rows.get(rNum)
      val cells = row.findElements(By.tagName("td"))
      for (cNum <- 0 until cells.size) {
        val cell = cells.get(cNum)
        if (!(cell.getText.trim == ""))
          if (shortFormData.startsWith(cell.getText)) return rNum + 1
      }
    }
    -1 // data is not found

  }

  def clickOnText(text: String)(implicit webDriver : WebDriver): Unit = {
    log("Clicking on text " + text)
    getElementByText(text)(webDriver).click()
  }

  def getElementByText(locatorText: String)(implicit webDriver : WebDriver): WebElement = {
    val e = xpath("//*[text()='" + locatorText + "']").webElement
    e
  }

}


