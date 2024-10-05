package stepDefinition.Home;

import common.BaseTest;
import common.KeywordWeb;
import common.LoggerHelpers;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.slf4j.Logger;

public class StepLogin{
    private static final Logger logger = LoggerHelpers.getLogger();
    @Given("User navigate url")
    public void userNavigateUrl() {
        BaseTest.beforeTest("web");
    }

    @When("User login {string} {string} and show error {string} {string}")
    public void userLoginAndShowError(String account, String password, String m_account, String m_password) {
        KeywordWeb.sendKeys("INPUT_ACCOUNT",account);
        KeywordWeb.sendKeys("INPUT_PASSWORD",password);
        KeywordWeb.click("BTN_LOGIN");
        if (!m_account.isEmpty()) {
            boolean status_a = KeywordWeb.verifyElementVisible("MESS_ACCOUNT");
            boolean status_p = KeywordWeb.verifyElementPresent("MESS_PASSWORD");

            KeywordWeb.assertTrue(status_a,"Element not visible.");
            KeywordWeb.assertTrue(status_p,"Element visible.");

            KeywordWeb.assertEquals(m_password,"MESS_PASSWORD");
//            logger.info("1");
        }
        if (!m_password.isEmpty()) {
            boolean status_a = KeywordWeb.verifyElementPresent("MESS_ACCOUNT");
            boolean status_p = KeywordWeb.verifyElementVisible("MESS_PASSWORD");

            KeywordWeb.assertTrue(status_a,"Element visible.");
            KeywordWeb.assertTrue(status_p,"Element not visible.");

            KeywordWeb.assertEquals(m_account,"MESS_ACCOUNT");
//            logger.info("2");
        }
    }

    @When("User verify form")
    public void userVerifyForm() {
        KeywordWeb.waitForJQueryLoad(5L);
        //check title form
        KeywordWeb.assertEquals("ĐĂNG NHẬP","TITLE_FORM_LOGIN");

        //check input account and password
        KeywordWeb.verifyElementVisible("INPUT_ACCOUNT");
        KeywordWeb.verifyAttribute("INPUT_ACCOUNT","placeholder","Nhập tên tài khoản");
        boolean state_acc = KeywordWeb.verifyElementState("INPUT_ACCOUNT");
        KeywordWeb.assertTrue(state_acc,"Element not click.");

        KeywordWeb.verifyElementVisible("INPUT_PASSWORD");
        KeywordWeb.verifyAttribute("INPUT_PASSWORD","placeholder","Nhập mật khẩu");
        boolean state_pass = KeywordWeb.verifyElementState("INPUT_PASSWORD");
        KeywordWeb.assertTrue(state_pass,"Element not click.");

        // check icon eye
        KeywordWeb.sendKeys("INPUT_PASSWORD","AB123abc$#^%$^%$.,/ ");
        KeywordWeb.verifyElementVisible("ICON_EYE_CLOSE");
        KeywordWeb.verifyAttribute("INPUT_PASSWORD","type","password");
        KeywordWeb.click("ICON_EYE_CLOSE");
        KeywordWeb.verifyElementVisible("ICON_EYE_OPEN");
        KeywordWeb.verifyAttribute("INPUT_PASSWORD","type","text");

        //check remember password
        KeywordWeb.verifyElementVisible("LABEL_REMEMBER_PASSWORD");
        KeywordWeb.assertEquals("Nhớ mật khẩu","LABEL_REMEMBER_PASSWORD");

        KeywordWeb.verifyAttribute("REMEMBER_PASSWORD","aria-valuetext","unchecked");
        KeywordWeb.click("REMEMBER_PASSWORD");
        KeywordWeb.verifyAttribute("REMEMBER_PASSWORD","aria-valuetext","checked");

        //check button login
        boolean state_btn = KeywordWeb.verifyElementState("BTN_LOGIN");
        KeywordWeb.assertTrue(state_btn,"Element not enabled.");
        KeywordWeb.assertEquals("Đăng nhập","BTN_LOGIN");
    }
}
