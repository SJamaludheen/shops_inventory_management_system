package com.onrender;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/resources/features", dryRun = false,
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber-json-report.json"}, glue = {"com.onrender.model", "com.onrender.stepDefinitions"}, tags = "@dev")


public class TestRunner {
    @Test
    public void startTest() {
        System.out.println("Starting tests....");
    }

}