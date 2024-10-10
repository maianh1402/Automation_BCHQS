package pages.DanhMuc;

import common.KeywordWeb;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DM_DanTocPage {
    public KeywordWeb keyword = new KeywordWeb();
    public void reloadDM(){
        keyword.reLoadPage();
    }
    public void addNewItem(String content_ma, String content_ten, String content_mo_ta, String toast) {
        keyword.waitForJSLoad(30L);
        keyword.click("BTN_THEM_MOI");
        keyword.waitForJSLoad(30L);
        checkLabel("ICON_ADD","Thêm mới dân tộc");
        keyword.clearAndSendKeys("INPUT_CODE",content_ma);
        keyword.clearAndSendKeys("INPUT_NAME",content_ten);
        keyword.clearAndSendKeys("INPUT_DESCRIPTION",content_mo_ta);
        keyword.click("BTN_LUU");
        keyword.assertTrue(keyword.verifyElementVisible("TOAST_MESS"),"Element not visible.");
        keyword.assertEquals(toast,"TOAST_MESS");
    }
    public void navigate(String url) {
        keyword.navigateToUrl(url);
        keyword.verifyUrl(url);
    }
    public void checkLabel(String icon,String title) {
        keyword.waitForJSLoad(30L);
        keyword.verifyElementVisible(icon);
        keyword.assertEquals(title,"TITLE_DIALOG");

        String[] expectedLabels = {"Mã", "Tên", "Mô tả"};
        List<WebElement> labelList = keyword.getListElementVisible("//div[@is='ethnicity-dialog']//label");
        for (int i = 0; i < labelList.size(); i++) {
            String labelValue = labelList.get(i).getText();
            String expectedValue = expectedLabels[i];

            if (labelValue.equals(expectedValue)) {
                System.out.println("Input " + i + " matches: " + labelValue);
            } else {
                System.out.println("Input " + i + " does NOT match. Found: " + labelValue + ", Expected: " + expectedValue);
            }
        }
    }

    private void clickPage(){
        keyword.waitForJSLoad(30L);
        keyword.click("//c-dropdown[@class='dropdown dropup']");
        keyword.click("//ul[@class='min-w-max dropdown-menu show']/li[1]");
        keyword.waitForJSLoad(30L);
    }
    public void verifyNewItem(){
        clickPage();
        keyword.clickByJavaScript("BTN_SUA_DT");
        keyword.waitForJSLoad(30L);

        String[] expectedInput = {"Test_ma", "Test ten"};
        String[] expectedTextAria = {"Test mo ta"};
        List<WebElement> InputList = keyword.getListElementVisible("//div[@is='ethnicity-dialog']//input");
        List<WebElement> TextAriaList = keyword.getListElementVisible("//div[@is='ethnicity-dialog']//textarea");
        // input
        for (int i = 0; i < InputList.size(); i++) {
            String inputValue = InputList.get(i).getAttribute("aria-valuetext");
            String expectedValue = expectedInput[i];
            if (expectedValue.equals(inputValue)) {
                System.out.println("Input " + i + " matches: " + inputValue);
            } else {
                System.out.println("Input " + i + " does NOT match. Found: " + inputValue + ", Expected: " + expectedValue);
            }
        }
//        textaria
        for (int i = 0; i < TextAriaList.size(); i++) {
            String textariaValue = TextAriaList.get(i).getAttribute("aria-valuetext");
            String expectedValue = expectedTextAria[i];
            if (expectedValue.equals(textariaValue)) {
                System.out.println("Input " + i + " matches: " + textariaValue);
            } else {
                System.out.println("Input " + i + " does NOT match. Found: " + textariaValue + ", Expected: " + expectedValue);
            }
        }
    }

    public void sendKeysPopup(String icon,String content_code,String content_name,String content_description,String err_code,String err_name,String err_description) {
        keyword.waitForJSLoad(30L);
        keyword.clickByJavaScript(icon);
        keyword.waitForJSLoad(50L);
        keyword.clearAndSendKeysWithBackspace("INPUT_CODE",content_code);
        keyword.clearAndSendKeys("INPUT_NAME",content_name);
        keyword.clearAndSendKeys("INPUT_DESCRIPTION",content_description);

        keyword.waitForJSLoad(50L);
        keyword.verifyAttribute("INPUT_CODE","value",content_code);
        keyword.verifyAttribute("INPUT_NAME","value",content_name);
        keyword.verifyAttribute("INPUT_DESCRIPTION","value",content_description);

        keyword.waitForJSLoad(50L);
        keyword.assertEquals(err_code,"ERROR_CODE");
        keyword.assertEquals(err_name,"ERROR_NAME");
        keyword.assertEquals(err_description,"ERROR_DESCRIPTION");
    }
    public void verifyPopupOpen() {
        keyword.verifyElementVisible("//div[@role='dialog']");
    }
    public void sendKeysPopupAndClickSubmit(String icon,String content_code,String content_name) {
        keyword.waitForJSLoad(30L);
        keyword.clickByJavaScript(icon);
        keyword.waitForJSLoad(30L);
        keyword.clearAndSendKeysWithBackspace("INPUT_CODE",content_code);
        keyword.clearAndSendKeys("INPUT_NAME",content_name);
        keyword.waitForJSLoad(30L);
        keyword.click("BTN_LUU");
        keyword.waitForJSLoad(30L);

        String[] expectedError = {"", "Tên đã tồn tại",""};
        List<WebElement> ErrorList = keyword.getListElementPresence("//div[@is='ethnicity-dialog']//small");
        for (int i = 0; i < ErrorList.size(); i++) {
            String err = ErrorList.get(i).getText();
            String expectedValue = expectedError[i];
            if (expectedValue.equals(err)) {
                System.out.println("Error " + i + " matches: " + err);
            } else {
                System.out.println("Error " + i + " does NOT match. Found: " + err + ", Expected: " + expectedValue);
            }
        }
    }
    public void deletedItem(String icon,String content) {
        keyword.waitForJSLoad(50L);
        keyword.clickByJavaScript("BTN_XOA_DT");
        keyword.waitForJSLoad(30L);

        keyword.verifyElementVisible("ICON_DELETE");
        keyword.assertEquals("Xác nhận xóa","TITLE_DELETE");
        keyword.assertEquals(content,"CONTENT_DELETE");
        keyword.verifyElementVisible("ICON_XOA_X");
        keyword.assertEquals("Xóa","BTN_XOA");
        keyword.assertEquals("Hủy","BTN_HUY");
        keyword.waitForJSLoad(30L);

        keyword.click(icon);
        keyword.waitForJSLoad(30L);
        if(icon.equals("BTN_XOA")){
            keyword.assertTrue(keyword.verifyElementVisible("TOAST_MESS"),"Element not visible.");
            keyword.assertEquals("Xóa thành công","TOAST_MESS");
        }else {
            keyword.verifyElementVisible("BTN_XOA_DT");
        }
    }
}
