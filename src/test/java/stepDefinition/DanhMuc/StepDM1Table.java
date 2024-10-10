package stepDefinition.DanhMuc;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.BeforeClass;
import pages.DanhMuc.DM_DanTocPage;
import pages.Home.LoginPage;

public class StepDM1Table {
    public DM_DanTocPage datntoc = new DM_DanTocPage();
    public LoginPage login = new LoginPage();
    @BeforeClass
    @Given("User access DM {string}")
    public void userAccessDM(String url) {
        login.beforeTest();
        login.loginSuccess();
        datntoc.navigate(url);
    }

    @When("User add new item")
    public void userAddNewItem() {
        datntoc.addNewItem("CONTENT_MA","CONTENT_TEN","CONTENT_MO_TA","Thêm mới thành công");
    }

    @Then("User see item new add")
    public void userSeeItemNewAdd() {
        datntoc.verifyNewItem();
        datntoc.checkLabel("ICON_UPDATE","Cập nhật dân tộc");
    }

    @When("User add new item {string} {string} {string} see error {string} {string} {string}")
    public void userAddNewItemSeeError(String content_code,String content_name,String content_description,String err_code,String err_name,String err_description) {
        datntoc.sendKeysPopup("BTN_THEM_MOI",content_code, content_name, content_description, err_code, err_name, err_description);
    }

    @Then("User see that the item not successfully added.")
    public void userSeeThatTheItemNotSuccessfullyAdded() {
        datntoc.verifyPopupOpen();
    }

    @When("User created item {string} {string} and submit see error")
    public void userAddNewItemAndSubmitSeeError(String content_code,String content_name) {
        datntoc.sendKeysPopupAndClickSubmit("BTN_THEM_MOI",content_code,content_name);
    }

    @When("User updated item {string} {string} {string} see error {string} {string} {string}")
    public void userUpdatedItemSeeError(String content_code,String content_name,String content_description,String err_code,String err_name,String err_description) {
        datntoc.sendKeysPopup("BTN_SUA_DT",content_code, content_name, content_description, err_code, err_name, err_description);
    }

    @When("User updated item {string} {string} and submit see error")
    public void userUpdatedItemAndSubmitSeeError(String content_code,String content_name) {
        datntoc.sendKeysPopupAndClickSubmit("BTN_SUA_DT",content_code,content_name);
    }

    @Given("User access DM")
    public void userAccessDM() {
        datntoc.reloadDM();
    }

    @When("User click {string} deleted item")
    public void userClickDeletedItem(String icon) {
        datntoc.deletedItem(icon,"Bạn có chắc chắn muốn xóa dân tộc Test ten ?");
    }

    @Then("User do not see deleted items")
    public void userDoNotSeeDeletedItems() {
    }
}
