package common;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonStepDefinition {
    public static KeywordWeb keyword = new KeywordWeb();
    @When("User login {string} {string} and show {string}")
    public static void userLoginAndShow(String account, String password, String toast){
        keyword.sendKeys("INPUT_ACCOUNT",account);
        keyword.sendKeys("INPUT_PASSWORD",password);
        keyword.click("REMEMBER_PASSWORD");
        keyword.click("BTN_LOGIN");
        boolean status = keyword.verifyElementVisible("TOAST_MESS");
        keyword.assertTrue(status,"Element not visible.");
        keyword.assertEquals(toast,"TOAST_MESS");
    }

    @Then("User see {string} page")
    public void userSeePage(String url) {
        keyword.verifyUrl(url);
    }
}
