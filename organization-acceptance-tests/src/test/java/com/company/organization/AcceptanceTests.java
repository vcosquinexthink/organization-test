package com.company.organization;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
    plugin = {"pretty", "json:target/cucumber_report.json"})
public class AcceptanceTests {

}
