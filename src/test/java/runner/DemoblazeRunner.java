package runner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(
		features ="src//test//resources//features",
		glue = {"stepdef"},
		monochrome = true,
		dryRun = false,
		plugin = {"pretty",
				"html:target//report//htmlreport"}
		)

public class DemoblazeRunner extends AbstractTestNGCucumberTests{
  
}
