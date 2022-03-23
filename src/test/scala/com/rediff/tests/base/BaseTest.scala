package com.rediff.tests.base

import java.util.concurrent.TimeUnit

import com.rediff.tests.common.Reporting
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.time.{Seconds, Span}
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.selenium.WebBrowser
import com.rediff.tests.common.ExtentReports.getReporter

trait BaseTest extends AnyFlatSpec with Matchers with WebBrowser with BeforeAndAfterAll with BeforeAndAfterEach with Eventually {

  var webDriver:WebDriver = _
  val screenshotsDirectory:String =  "target/test-reports/screenshots"


  System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe")
  val timeout = 10
  setCaptureDir(screenshotsDirectory)

  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  override def beforeEach(): Unit = {
    webDriver =  openBrowser("Chrome")
  }

  val className = getClass.getSimpleName

  val extent = getReporter(className)

  /*Code to get the name of the current test*/
  private val _currentTestName = new ThreadLocal[String]

  override def withFixture(test: NoArgTest) = {
    _currentTestName.set(test.name)
    val outcome = super.withFixture(test)
    _currentTestName.set(null)
    outcome
  }

  protected def currentTestName: String = {
    val testName = _currentTestName.get()
    assert(testName != null, "currentTestName should only be called in a test")
    testName
  }

  def openBrowser(browser:String) : WebDriver = {
    if(browser.equals("Chrome")){
      val webDriver =  getChromeDriver()
      webDriver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS)
      webDriver}
    else {
      val webDriver =  getFirefoxDriver()
      webDriver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS)
      webDriver}
  }


  def getChromeDriver() : WebDriver = {
    val options = new ChromeOptions()
    options.addArguments("--allow-running-insecure-content")
    options.addArguments("ignore-certificate-errors")
    options.addArguments("--start-maximized")
    new ChromeDriver(options)
  }


  def getFirefoxDriver() : WebDriver = {
    new FirefoxDriver()
  }

  protected def initiateLogs(): Unit = {
    Reporting.setTest(extent,currentTestName)
  }

  protected def failTest(msg:String): Unit = {
    Reporting.logFailure(msg)(webDriver)
    fail("Test failed because of the reason "+msg)
  }

  override def afterEach(): Unit = {
    quit()(webDriver)
  }

  override def afterAll(): Unit = {
    extent.flush()
  }




}
