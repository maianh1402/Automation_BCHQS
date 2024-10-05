package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesHelpers {
    private static Properties properties;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;

    static String projectPath = System.getProperty("user.dir") + "/";
    private static final String propertiesPath = "src/test/resources/data.properties";

    public static void setPropertiesFile() {
        properties = new Properties();
        try (FileInputStream fileIn = new FileInputStream(projectPath + propertiesPath)) {
            properties.load(fileIn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
    public static String getPropValue(String key){
        if (properties == null) {
            setPropertiesFile();
        }
        return properties.getProperty(key);
    }
    public static void setPropValue(String key, String value) {
        try (FileOutputStream fileOut = new FileOutputStream(projectPath + propertiesPath)) {
            if (properties == null) {
                setPropertiesFile();
            }
            properties.setProperty(key, value);
            properties.store(fileOut, "set new value");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }
}
