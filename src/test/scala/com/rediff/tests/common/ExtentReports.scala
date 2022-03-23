package com.rediff.tests.common

import java.io.File

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import java.text.SimpleDateFormat
import java.util.Date
import org.scalatestplus.selenium.WebBrowser


object ExtentReports extends WebBrowser {
  var extent:ExtentReports = _
  var reporter : ExtentSparkReporter =_


  def getReporter(className:String) : ExtentReports = {

    if (!className.endsWith("$")) {

      if (extent == null) {

        val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = new Date()
        val reportsFolder = dateFormat.format(date).replaceAll(":", "_")

        val reportsDirectoryPath = "target/test-reports/extent-reports//" + reportsFolder+"//reports"


        val directory = new File(reportsDirectoryPath)


        if (!directory.exists)
          directory.mkdirs()

        reporter = new ExtentSparkReporter(reportsDirectoryPath)
        reporter.config.setReportName("Scala Rediff Framework")
        reporter.config.setDocumentTitle("Scala Rediff Framework Reports")
        extent = new ExtentReports
        extent.attachReporter(reporter)
      }
    }
    extent
  }

}
