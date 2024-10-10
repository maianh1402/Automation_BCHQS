package runner.DanhMuc;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/java/features/DanhMuc",
        glue = {
                "stepDefinition.DanhMuc",
                "stepDefinition",
                "hooks"
        },
        plugin = {"pretty", "html:target/cucumber-html-report.html"}
)

@Test
public class RunDanToc extends AbstractTestNGCucumberTests {
}
