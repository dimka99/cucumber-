package CucumberTests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/CreateAWSVirtualServer", 
	glue= {"Def_CreateAWSVirtualServerWindows"}
		
		)
public class CT_CreateAWSVirtualServerWindows {

}
