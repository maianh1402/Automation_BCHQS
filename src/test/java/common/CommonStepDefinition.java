package common;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonStepDefinition {
    @When("User login {string} {string} and show {string}")
    public void userLoginAndShow(String account, String password, String toast){
        KeywordWeb.sendKeys("INPUT_ACCOUNT",account);
        KeywordWeb.sendKeys("INPUT_PASSWORD",password);
        KeywordWeb.click("REMEMBER_PASSWORD");
        KeywordWeb.click("BTN_LOGIN");
        boolean status = KeywordWeb.verifyElementVisible("TOAST_MESS");
        KeywordWeb.assertTrue(status,"Element not visible.");
        KeywordWeb.assertEquals(toast,"TOAST_MESS");
    }

    @Then("User see {string} page")
    public void userSeePage(String url) {
        KeywordWeb.verifyUrl(url);
    }
}
