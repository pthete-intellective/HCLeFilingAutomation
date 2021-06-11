package com.stateofconnecticut.selenium.Application.Modules;

import com.aventstack.extentreports.Status;
import com.stateofconnecticut.selenium.config.Configuration;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import com.stateofconnecticut.selenium.utilities.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;


public abstract class BaseAction {
    protected WebDriver driver;
    protected Configuration conf ;

    public BaseAction(WebDriver driver, Configuration conf) {
        this.driver = driver;
        this.conf = conf;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Configuration getConf() {
        return conf;
    }
    public abstract String getFormName();
    public void selectPUAR(ConfigTestRunner configTestRunner) {
        if (configTestRunner.getTestCase().get("ApplicationType").contains("PURA")){
            try {
                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("Login", "PURAHome"));
                if (getWebElement("MatterFiling", "matterFillingLink").isDisplayed()) {
                    configTestRunner.getChildTest().log(Status.PASS, "PURA home link is selected successfully.");
                } else {
                    try {
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        configTestRunner.getChildTest().log(Status.FAIL, " PURA home link is not selecte successfully" + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }
        }else if(configTestRunner.getTestCase().get("ApplicationType").contains("BETP")){
            try {
                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("Login", "BETPHome"));
                if (getWebElement("MatterFiling", "matterFillingLink").isDisplayed()) {
                    configTestRunner.getChildTest().log(Status.PASS, "BETP home link is selected successfully.");
                } else {
                    try {
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        configTestRunner.getChildTest().log(Status.FAIL, " BETP home link is not selecte successfully" + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {

            }
        }
    }
    public WebElement getWebElement(String name) {
        return getConf().getWebElement(getDriver(), getFormName(), name);
    }
    public List<WebElement>  getWebElements(String name) {
        List<WebElement> webElement=getConf().getWebElementes(getDriver(), getFormName(), name);
        return webElement;
    }
    public WebElement getWebElement(String formName, String name) {

        return getConf().getWebElement(getDriver(), formName, name);
    }
    public WebElement getspanWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//span[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement getTDWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//td[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public boolean fnWaitForVisibility(WebElement element , int waitFor){
        boolean visible = false;
        WebDriverWait wait = new WebDriverWait(driver,waitFor);
        wait.until(ExpectedConditions.visibilityOf(element));
        if(element.isDisplayed())
            return true;
        else
            return false;
    }
    public void sleep(int timer){
        try{
            Thread.sleep(timer);
        }catch (Exception e){

        }
    }
    public WebElement getSpanContainsWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//span[contains(text(), '" + text + "')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public void fnFileUploadFunctionality(ConfigTestRunner configTestRunner ,String FileLocation){
        StringSelection fileSelection = new StringSelection(FileLocation);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection,null);
        try{
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public WebElement getSpanContainsWithTextfiledBy(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//div[@id='FilingonBehalfOf']//span[contains(text(), '" + text + "')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    protected WebElement getVisibleElement(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        WebElement rE = null;
        int elementsSize = elements.size();
        for (int i = 0; i < elementsSize; i++) {
            System.out.println("test" + i);
            try {
                wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(elements.get(i))));
                break;
            } catch (Exception e) {
                //handle exception however you like
            }
        }
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                System.out.println("found and assigning to rE");
                rE = element;
                break;
            }
        }
        return rE;
    }
    public void executeExtJsClick(WebDriver driver, WebElement element) {
        String attr = element.getAttribute("id");
        ((JavascriptExecutor)driver).executeScript("document.getElementById('" + attr + "').click()");
    }
    public  String theMonth(int month){
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
    public void fnmakeElementVisibleByxpath(WebDriver driver,WebElement element){
        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor)driver).executeScript(js,element);
    }
    protected void fillForm(List<String> fields, String formName, ConfigTestRunner configTestRunner){
        Configuration conf = getConf();
        for (String field : fields) {
            String xpath = conf.getXpath(formName, field);
            String value = configTestRunner.getTestData().get(field);
            String type = conf.getType(formName, field);
            WebElement element = getDriver().findElement(By.xpath(xpath));
            if(type == null){
                try {
                    element.clear();
                    element.sendKeys(value);
                    configTestRunner.getChildTest().log(Status.INFO, "Data Enter to the field " + field + " is :" + value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (type.equals(Configuration.COMBO_SELECT_TYPE)) {
                WebElement item = null;
                configTestRunner.getCommonMethods().selectComboboxSendKeys(element,configTestRunner,value);
            }
        }

    }


}
