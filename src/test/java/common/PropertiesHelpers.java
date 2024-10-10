package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class PropertiesHelpers {
    private static Properties properties;

    static String projectPath = System.getProperty("user.dir") + "/";
    private static final String propertiesPath = "src/test/resources/data.properties";

    public static void setPropertiesFile() {
        properties = new Properties();
        try (FileInputStream fileIn = new FileInputStream(projectPath + propertiesPath);
             InputStreamReader reader = new InputStreamReader(fileIn, "UTF-8")) {
            properties.load(reader);
        } catch (Exception e) {
            System.out.println("Error loading properties file: " + projectPath + propertiesPath);
            e.printStackTrace();
        }
    }

    public static String getPropValue(String key){
        if (properties == null) {
            setPropertiesFile();
        }
        String value = null;
        try {
            value = properties.getProperty(key);
        } catch (Exception e) {
            System.out.println("Error getting property value for key: " + key);
            e.printStackTrace();
        }
        return value;
    }

    public static void setPropValue(String key, String value) {
        if (properties == null) {
            setPropertiesFile();  // Khởi tạo nếu chưa khởi tạo
        }
        try (FileOutputStream fileOut = new FileOutputStream(projectPath + propertiesPath);
             OutputStreamWriter writer = new OutputStreamWriter(fileOut, "UTF-8")) {
            properties.setProperty(key, value);
            properties.store(writer, "Set new value");
        } catch (Exception e) {
            System.out.println("Error setting property value for key: " + key);
            e.printStackTrace();
        }
    }
}
