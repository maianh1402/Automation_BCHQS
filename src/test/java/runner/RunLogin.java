package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/java/features/Home",
        glue = {
                "stepDefinition.Home",
                "stepDefinition",
                "hooks"
        },
        plugin = {"pretty", "html:target/cucumber-html-report.html"}
)

@Test
public class RunLogin extends AbstractTestNGCucumberTests {
}
