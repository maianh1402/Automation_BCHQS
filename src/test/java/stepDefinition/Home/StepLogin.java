package stepDefinition.Home;

import common.LoggerHelpers;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import pages.Home.LoginPage;

public class StepLogin{
    private static final Logger logger = LoggerHelpers.getLogger();
    @Given("User navigate url")
    public void userNavigateUrl() {
        LoginPage.beforeTest("web");
    }

    @When("User login {string} {string} and show error {string} {string}")
    public void userLoginAndShowError(String account, String password, String m_account, String m_password) {
        LoginPage.loginFaile(account, password, m_account, m_password);
    }

    @When("User verify form")
    public void userVerifyForm() {
        LoginPage.verifyFormLogin();
    }
}
