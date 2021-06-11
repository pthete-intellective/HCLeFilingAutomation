package com.stateofconnecticut.selenium.config;

import com.stateofconnecticut.selenium.utilities.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class Configuration {
    public static String config;
    public static String driverPath;
    public static String login;
    public static String password;

    private static final String XPATH_FILE = "field-xpath.properties";
    private static final String TYPES_FILE = "field-types.properties";

    public static final String COMBO_SELECT_TYPE = "ComboSelect";
    public static final String COMBO_SEARCH_SELECT_TYPE = "ComboSearchSelect";
    public static final String COMBO_BY_VISIBLE_STYLE_SELECT_TYPE = "ComboByVisibleStyleSelect";

    private Properties fieldXpath = ConfigurationReader.readPropertiesFile(XPATH_FILE);
    private Properties fieldTypes = ConfigurationReader.readPropertiesFile(TYPES_FILE);

    private Configuration() {
    }

    public static Configuration read() {
        Configuration configuration = new Configuration();
        configuration.fieldXpath = ConfigurationReader.readPropertiesFile(XPATH_FILE);
        configuration.fieldTypes = ConfigurationReader.readPropertiesFile(TYPES_FILE);
        return configuration;
    }

    public String getXpath(String formName, String fieldName) {
        String key = formName + "." + fieldName;
        return checkValueForNull(key, fieldXpath.getProperty(key));
    }

    public String getType(String formName, String fieldName) {
        String key = formName + "." + fieldName;
        return fieldTypes.getProperty(key);
    }

    private String checkValueForNull(String key, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Key " + key + " value not found.");
        }
        return value;
    }

    public WebElement getWebElement(WebDriver driver, String formName, String name) {
        String xpath = getXpath(formName, name);
        return driver.findElement(By.xpath(xpath));
    }
    public List<WebElement> getWebElementes(WebDriver driver, String formName, String name){
        List<WebElement> elm =null;
        String xpath = getXpath(formName, name);
        try{
            elm = driver.findElements(By.xpath(xpath));
        }catch (NoSuchElementException e){
            elm=null;
        }
        return elm;
    }
}
