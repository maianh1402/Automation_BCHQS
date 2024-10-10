package stepDefinition.Home;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import pages.Home.LoginPage;

public class StepLogin{
    LoginPage loginPage;
    @Before
    public void setupLogin(){
        loginPage = new LoginPage();
    }
    @When("User login success")
    public void userLoginSuccess() {
        loginPage.loginSuccess();
    }

    @Given("User navigate url")
    public void userNavigateUrl() {
        loginPage.beforeTest();
    }

    @When("User login {string} {string} and show error {string} {string}")
    public void userLoginAndShowError(String account, String password, String m_account, String m_password) {
        loginPage.loginFailed(account, password, m_account, m_password);
    }

    @When("User verify form")
    public void userVerifyForm() {
        loginPage.verifyFormLogin();
    }
}
