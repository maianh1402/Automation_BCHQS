package stepDefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import common.KeywordWeb;

public class common {
    KeywordWeb keyword;
    @Before
    public void common(){
        keyword = new KeywordWeb();
    }

    @Then("User see {string} page")
    public void userSeePage(String url) {
        keyword.verifyUrl(url);
    }
}
