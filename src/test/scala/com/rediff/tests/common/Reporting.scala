package com.rediff.tests.common

import java.text.SimpleDateFormat
import java.util.Date

import com.aventstack.extentreports.{ExtentReports, ExtentTest, MediaEntityBuilder, Status}
import com.rediff.tests.base.BaseTest
import org.openqa.selenium.WebDriver



object Reporting extends BaseTest{

  var test: ExtentTest =_

  def setTest(extent:ExtentReports,testName: String): Unit = {
    test = extent.createTest(testName)
    test.assignCategory(getClass.getSimpleName)
  }

  def log(msg: String): Unit = {
    test.log(Status.INFO, msg)
  }

  def logPass(msg: String): Unit = {
    test.log(Status.PASS, msg)
  }

  def logFailure(msg: String)(implicit webDriver :WebDriver) : Unit = {
    test.log(Status.FAIL, msg)
    takeScreenshot()
    fail(msg) //fail scala test
  }


  def takeScreenshot()(implicit webDriver:WebDriver): Unit = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = new Date()
    val screenshotFileName: String = "Screenshot_"+dateFormat.format(date).replaceAll(":", "_") + ".png"

    val screenshotDirectory = System.getProperty("user.dir")+"//"+screenshotsDirectory+"//"+screenshotFileName
    capture to screenshotFileName

    test.fail("<p><font color=red>" + " Click the below link or check the latest  report folder namednamed </font></p>", MediaEntityBuilder.createScreenCaptureFromPath(screenshotDirectory).build)
  }

}

