package pages.Home;

import common.KeywordWeb;
import common.PropertiesHelpers;

public class LoginPage {
    public KeywordWeb keyword = new KeywordWeb();

    public void beforeTest() {
        keyword.openBrowser(PropertiesHelpers.getPropValue("BROWSER_NAME"), PropertiesHelpers.getPropValue("BASE_URL"));
//        if (devices.equalsIgnoreCase("mobile")){
//            resizeBrowser(319,848);
//        }else {
        keyword.maximizeWindow();
//        }
    }
    public void loginSuccess(){
        keyword.sendKeys("INPUT_ACCOUNT","ACCOUNT");
        keyword.sendKeys("INPUT_PASSWORD","PASSWORD");
        keyword.click("REMEMBER_PASSWORD");
        keyword.click("BTN_LOGIN");
        boolean status = keyword.verifyElementVisible("TOAST_MESS");
        keyword.assertTrue(status,"Element not visible.");
        keyword.assertEquals("Đăng nhập thành công","TOAST_MESS");
    }
    public void loginFailed(String account, String password, String m_account, String m_password){
        keyword.sendKeys("INPUT_ACCOUNT",account);
        keyword.sendKeys("INPUT_PASSWORD",password);
        keyword.click("BTN_LOGIN");
        if (!m_account.isEmpty()) {
            boolean status_a = keyword.verifyElementVisible("MESS_ACCOUNT");
            boolean status_p = keyword.verifyElementPresent("MESS_PASSWORD");

            keyword.assertTrue(status_a,"Element not visible.");
            keyword.assertTrue(status_p,"Element visible.");

            keyword.assertEquals(m_password,"MESS_PASSWORD");
//            logger.info("1");
        }
        if (!m_password.isEmpty()) {

            keyword.assertTrue(keyword.verifyElementPresent("MESS_ACCOUNT"),"Element visible.");
            keyword.assertTrue(keyword.verifyElementVisible("MESS_PASSWORD"),"Element not visible.");

            keyword.assertEquals(m_account,"MESS_ACCOUNT");
//            logger.info("2");
        }
    }
    public void verifyFormLogin(){
        keyword.waitForJSLoad(5L);
        //check title form
        keyword.assertEquals("ĐĂNG NHẬP","TITLE_FORM_LOGIN");

        //check input account and password
        keyword.verifyElementVisible("INPUT_ACCOUNT");
        keyword.verifyAttribute("INPUT_ACCOUNT","placeholder","Nhập tên tài khoản");
        keyword.assertTrue(keyword.verifyElementState("INPUT_ACCOUNT"),"Element not click.");

        keyword.verifyElementVisible("INPUT_PASSWORD");
        keyword.verifyAttribute("INPUT_PASSWORD","placeholder","Nhập mật khẩu");
        keyword.assertTrue(keyword.verifyElementState("INPUT_PASSWORD"),"Element not click.");

        // check icon eye
        keyword.sendKeys("INPUT_PASSWORD","AB123abc$#^%$^%$.,/ ");
        keyword.verifyElementVisible("ICON_EYE_CLOSE");
        keyword.verifyAttribute("INPUT_PASSWORD","type","password");
        keyword.click("ICON_EYE_CLOSE");
        keyword.verifyElementVisible("ICON_EYE_OPEN");
        keyword.verifyAttribute("INPUT_PASSWORD","type","text");

        //check remember password
        keyword.verifyElementVisible("LABEL_REMEMBER_PASSWORD");
        keyword.assertEquals("Nhớ mật khẩu","LABEL_REMEMBER_PASSWORD");

        keyword.verifyAttribute("REMEMBER_PASSWORD","aria-valuetext","unchecked");
        keyword.click("REMEMBER_PASSWORD");
        keyword.verifyAttribute("REMEMBER_PASSWORD","aria-valuetext","checked");

        //check button login
        keyword.assertTrue(keyword.verifyElementState("BTN_LOGIN"),"Element not enabled.");
        keyword.assertEquals("Đăng nhập","BTN_LOGIN");
    }
}
