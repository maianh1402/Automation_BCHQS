package hooks;

import common.KeywordWeb;
import common.LoggerHelpers;
import io.cucumber.java.*;
import org.slf4j.Logger;
import pages.Home.LoginPage;

public class CucumberHooks {
    private static Logger logger = LoggerHelpers.getLogger();
    @BeforeAll
    public static void beforeAll() {
        logger.info("================ beforeAll ================");
    }

    @AfterAll
    public static void afterAll() {
        logger.info("================ afterAll ================");
        KeywordWeb.closeBrowser();
    }

    @Before
    public void beforeScenario() {
        logger.info("================ beforeScenario ================");
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("================ afterScenario ================");
//        KeywordWeb.closeBrowser();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        logger.info("================ beforeStep ================");
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        logger.info("================ afterStep ================");
    }
}
