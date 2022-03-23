package com.rediff.tests.common

import com.rediff.tests.base.BaseTest
import com.rediff.tests.common.Util.{clickElement, clickOnText, getRowNumWithCellData, selectValueFromDropDown, typeText, waitForPageToLoad}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.rediff.tests.common.Reporting._
import java.text.SimpleDateFormat



object Stock extends BaseTest{

  val e: WebLocators.type = WebLocators

  def findCurrentStockQuantity(companyName: String)( implicit webDriver: WebDriver): Int = {
    log("Finding current stock quantity for " + companyName)
    val row = verifyStockPresent(companyName)
    if(row == -1) {
      logFailure("Stock not found in the list " + companyName)
      return 0
    }

    val quantity = cssSelector("table#stock > tbody"+ " > tr:nth-child(" + row + ") >td:nth-child(4)").webElement.getText
    log("Current stock Quantity " + quantity)
    quantity.toInt
  }

  def verifyStockPresent(companyName: String)( implicit webDriver: WebDriver): Int = {
    val row = getRowNumWithCellData(e.stockTable.webElement, companyName)
    if (row == -1) {
      log("Current Stock Quantity is 0 as Stock not present in list")
      return 0
    }
    row
  }



  def addStock(companyName: String,date:String,stockQuantity:Int,stockPrice:Int)(implicit webDriver: WebDriver): Unit = {
    log("In add stock method and adding company "+companyName + " and stock "+stockQuantity +" and price "+stockPrice)

    val companyNameStartsWith = companyName.substring(0,10)
    clickElement(e.addStock.webElement )
    typeText(e.addStockName.webElement,companyNameStartsWith)
    Thread.sleep(2000)
    clickOnText(companyName)
    clickElement(e.stockPurchaseDate.webElement)
    selectDateFromCalendar(date)
    typeText(e.addStockQty.webElement, stockQuantity.toString)
    typeText(e.addStockPrice.webElement, stockPrice.toString)
    clickElement(e.addStockButton.webElement)
    log("Added stock for company "+companyName + " and stock "+stockQuantity +" and price "+stockPrice)
    waitForPageToLoad()
  }

  def selectDateFromCalendar(date: String)(implicit webDriver: WebDriver): Unit = {
    log("Selecting Date " + date)
    try {
      val dateToSel = new SimpleDateFormat("d-MM-yyyy").parse(date)
      val day = new SimpleDateFormat("d").format(dateToSel)
      val month = new SimpleDateFormat("MMMM").format(dateToSel)
      val year = new SimpleDateFormat("yyyy").format(dateToSel)
      val monthYearToBeSelected = month + " " + year
      val monthYearDisplayed = e.monthYear.webElement.getText

      selectMonthYear(monthYearToBeSelected,monthYearDisplayed)


      xpath("//td[text()='" + day + "']").webElement.click()
    } catch {
      case e: Exception =>
        // TODO Auto-generated catch block
        e.printStackTrace()
        log("Failed to select date")
    }
  }

  @scala.annotation.tailrec
  def selectMonthYear(monthYearToBeSelected: String, monthYearDisplayed:String)(implicit webDriver: WebDriver): Unit = {

    if(!(monthYearToBeSelected == monthYearDisplayed)) {
      clickElement(e.dateBackButton.webElement)
      val monthYearDisplayed = e.monthYear.webElement.getText
      selectMonthYear(monthYearToBeSelected,monthYearDisplayed)
    }
  }


  def goToBuySell(companyName:String)(implicit webDriver: WebDriver): Unit = {
    log("Selecting the company row " + companyName)
    val row = getRowNumWithCellData(e.stockTable.webElement, companyName)
    if (row == -1)
      log("Stock not present in list")
    click on   cssSelector("table#stock > tbody > tr:nth-child(" + row + ") >td:nth-child(1)").webElement

    val wait = new WebDriverWait(webDriver, 5)
    wait.until(ExpectedConditions.visibilityOf(cssSelector("table#stock  tr:nth-child(" + row + ") input.buySell").webElement))
    cssSelector("table#stock  tr:nth-child(" + row + ") input.buySell").webElement.click()
  }

  def chooseAction(companyName:String,action:String="Buy")(implicit webDriver: WebDriver): Unit = {
    log("Choosing action "+action + " for company "+companyName)
    if (action.equals("Sell")) {
      selectValueFromDropDown(e.equityAction.webElement, "Sell")
    }
    else {
      selectValueFromDropDown(e.equityAction.webElement, "Buy")
    }

  }

  def modifyStock(date:String,stockQuantity:Int,stockPrice:Int)(implicit webDriver: WebDriver): Unit = {
    log("In modify stock method stock "+stockQuantity +" and price "+stockPrice +" and date "+date)

    clickElement(e.buySellCalendar.webElement)
    selectDateFromCalendar(date)
    Thread.sleep(1000)
    typeText(e.buySellQty.webElement, stockQuantity.toString)
    typeText(e.buySellPrice.webElement, stockPrice.toString)
    clickElement(e.buySellStockButton.webElement)
    waitForPageToLoad()
  }


  def verifyStockQuantity(action: String, companyName :String, stockQuantity:Int,quantityBeforeModification:Int)(implicit webDriver: WebDriver): Unit = {

    val quantityAfterModification = findCurrentStockQuantity(companyName)(webDriver)

    val modifiedQuantity = stockQuantity
    var expectedModifiedQuantity = 0


    if (action == "Buy")
      expectedModifiedQuantity = quantityAfterModification - quantityBeforeModification

    else if (action == "Sell")
      expectedModifiedQuantity = quantityBeforeModification - quantityAfterModification

    log("Quantity before modification---> " + quantityBeforeModification)
    log("Quantity after modification---> " + quantityAfterModification)

    if (modifiedQuantity != expectedModifiedQuantity)
      logFailure("Stock Quantity did not change as per expected. Expected quanity  "+expectedModifiedQuantity +"" +
        " changed quantity" + modifiedQuantity)
    log("Stock Quantity changed as per expected. Expected quantity  "+expectedModifiedQuantity +"" +
      " changed quantity " + modifiedQuantity)
  }

  def verifyTransactionHistory(action: String,companyName:String,stockQuantity:Int)(implicit webDriver :WebDriver): Unit  = {
    var expectedQuantity = stockQuantity.toString
    goToTransactionHistory(companyName)(webDriver)

    val changedQuantityDisplayed = e.latestShareChangeQuantity.webElement.getText


    if (action == "Sell")
      expectedQuantity = "-" + stockQuantity

    log("Expected quantity "+expectedQuantity)


    if (!changedQuantityDisplayed.equals(expectedQuantity))
      logFailure("The expected changed quantity is "+expectedQuantity + " but the actual quantity changed is "+changedQuantityDisplayed)
    logPass("Transaction History OK")
  }

  def goToTransactionHistory(companyName: String)(implicit webDriver :WebDriver): Unit = {
    log("Selecting the company row " + companyName)
    val row = getRowNumWithCellData(e.stockTable.webElement, companyName)
    if (row == -1) {
      println("Stock not present in list")
      // report failure
    }
    click on  cssSelector("table#stock  tr:nth-child(" + row + ") >td:nth-child(1)").webElement
    val wait = new WebDriverWait(webDriver, 5)
    wait.until(ExpectedConditions.visibilityOf(cssSelector("table#stock  tr:nth-child(" + row + ") input.equityTransaction").webElement))
    click on cssSelector("table#stock  tr:nth-child(" + row + ") input.equityTransaction").webElement
  }

  class StockData(val name : String,val street : String, val city : String, val postalCode : String, val region : String, val country : String, val expirationMonth : String, val expirationYear : String)(implicit webDriver : WebDriver) {
  }

}

