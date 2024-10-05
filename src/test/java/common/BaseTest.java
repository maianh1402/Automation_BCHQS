package common;

public class BaseTest{
    public static KeywordWeb keyword;

    public static void beforeTest(String devices) {
        keyword.openBrowser(PropertiesHelpers.getPropValue("BROWSER_NAME"), PropertiesHelpers.getPropValue("BASE_URL"));
        if (devices.equalsIgnoreCase("mobile")){
            keyword.resizeBrowser(319,848);
        }else {
            keyword.maximizeWindow();
        }
    }
}
