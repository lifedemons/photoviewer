package com.photoviewer.test;

import com.photoviewer.BuildConfig;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "features", // Scenarios
    glue = { "com.photoviewer.steps" }, // Steps
    format = {
        "pretty", // Cucumber report formats and location to store them on device
        "html:" + CucumberTestCase.REPORTS_PATH + "cucumber-html-report",
        "json:" + CucumberTestCase.REPORTS_PATH + "cucumber.json",
        "junit:" + CucumberTestCase.REPORTS_PATH + "cucumber.xml"
    },
    tags = { "@functional-scenarios" })
class CucumberTestCase {
  public static final String REPORTS_PATH =
      "/data/data/" + BuildConfig.APPLICATION_ID + "/cucumber-reports/";
}
