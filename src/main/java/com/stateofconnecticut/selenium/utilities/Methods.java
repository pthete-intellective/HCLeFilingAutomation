package com.stateofconnecticut.selenium.utilities;

import com.aventstack.extentreports.Status;
import org.apache.commons.logging.Log;
import org.openqa.selenium.*;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.stateofconnecticut.selenium.utilities.Constants.AJAX_TIMEOUT;
public class Methods {
    protected static final Log logger = LogFactory.getLog(Methods.class);

    public WebDriver driver ;
    public synchronized WebDriver startBrowser(ConfigTestRunner configTestRunner,String browser) {
        if(browser.trim().equalsIgnoreCase("Chrome")){
            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
            options.addArguments("--start-maximized");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable.dev.shm.usage");
            options.setExperimentalOption("excludeSwithes", new String[] {"enable-automation"});
            options.setExperimentalOption("useAutomationExtension",false);
            String driverpath = System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\chromedriver.exe";
            System.setProperty("webdriver.chrome.driver",driverpath);
            driver= new ChromeDriver();
            setImplicitlyWait(300);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

        }else if(browser.trim().equalsIgnoreCase("IE")){
            InternetExplorerOptions ieOption = new InternetExplorerOptions();
            ieOption.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
            ieOption.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            ieOption.setCapability("ignoreProtectedModeSettings", true);
            String driverpath = System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\IEDriverServer.exe";
            System.setProperty("webdriver.ie.driver",driverpath);
            driver = new InternetExplorerDriver(ieOption);
            setImplicitlyWait(100);
            driver.manage().deleteAllCookies();
        }else{
            System.out.println("No Browser is selected for opening");
        }

        return driver;
    }

    public  boolean waitForAjaxLoaded() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        long start = System.currentTimeMillis();
        long finish = start + AJAX_TIMEOUT;
        boolean loaded = false;

        while (System.currentTimeMillis() < finish && !loaded) {
            if (Boolean.FALSE.equals(((JavascriptExecutor)(driver)).executeScript("return Ext.Ajax.isLoading()"))) {
                loaded = true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return loaded;
    }
    public  void setImplicitlyWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }
    public void setFocus(WebElement element, String value) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.sendKeys(value);
        action.build().perform();
    }
    public void setFocusEnter(WebElement element, String value) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.sendKeys(value);
        action.sendKeys(Keys.DOWN);
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }
    public void setdataSendKeysEnter(WebElement element, String value) {
        element.sendKeys(value);
        try {
            Thread.sleep(300);
        }catch (Exception e){

        }
//        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);

    }
    public void setdataSendKeysEnterkeyDown(WebElement element, String value) {
        element.sendKeys(value);
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);

    }
    public void setFocusWithoutClick(WebElement element, String value) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(value);
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }
    public void fnMoveToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.build().perform();
    }
    public void setFocusClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.build().perform();
    }
    public void setFocusSelectbar(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
//        action.click();
        action.sendKeys(Keys.SPACE);
        action.build().perform();
    }
    public void sendKeysEnter(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }
    public void sendKeysPageDown(){
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN);
        action.build().perform();
    }
    public void setFocusdblClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.doubleClick();
        action.build().perform();
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
    public void waitAndContextClick(final WebElement element, int waitfor) {
        WebDriverWait wait = new WebDriverWait(driver, waitfor);
        final Actions action = new Actions(driver);
        wait.until(new ExpectedCondition<WebElement>() {
            public ExpectedCondition<WebElement> visibilityOfElement = ExpectedConditions.visibilityOf(element);
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    WebElement elementx = this.visibilityOfElement.apply(driver);
                    if (elementx == null) {
                        return null;
                    }
                    if (elementx.isDisplayed() && elementx.isEnabled())  {
                        action.contextClick(elementx).build().perform();
                        return elementx;
                    } else {
                        return null;
                    }
                } catch (WebDriverException e) {
                    return null;
                }
            }
        });
    }
    public void sendKeysDownClick(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(Keys.DOWN);
        action.build().perform();
    }
    public void sendKeysDownClickEnter(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(Keys.DOWN);
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }

    public void waitAndClick(final WebElement element, int waitfor) {
        WebDriverWait wait = new WebDriverWait(driver, waitfor);
        wait.until(new ExpectedCondition<WebElement>() {
            public ExpectedCondition<WebElement> visibilityOfElement = ExpectedConditions.visibilityOf(element);
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    WebElement elementx = this.visibilityOfElement.apply(driver);
                    if (elementx == null) {
                        return null;
                    }
                    if (elementx.isDisplayed() && elementx.isEnabled())  {
                        elementx.click();
                        return elementx;
                    } else {
                        return null;
                    }
                } catch (WebDriverException e) {
                    return null;
                }
            }
        });
    }
    public WebElement getlinkWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//a[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement gettdWithText(ConfigTestRunner configTestRunner,String text) {
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
    public WebElement getlinkWithTextfirstele(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("(//a[text() = '" + text + "'])[1]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement getlinkContainsWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//a[contains(text(), '" + text + "')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement saveImage(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//a[contains(@onclick,'" +text+"')]//img[@alt='Click to save schedule']"));

        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement getdivContainsWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement = null;
        try {
            webElement = driver.findElements(By.xpath("//div[contains(text(), '" + text + "')]"));
        } catch (Exception e) {
            configTestRunner.getChildTest().log(Status.FAIL, text + " :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement getdivContainsWithTextTabSel(ConfigTestRunner configTestRunner,String prevColName,String text) {
        List<WebElement> webElement=null;
        try{
            //webElement = driver.findElements(By.xpath("//div[contains(text(), '" + text + "')]"));
            webElement = driver.findElements(By.xpath("//div[text()='"+prevColName+"']/following::div[contains(text(),'"+text+"')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

    }
    public WebElement getElement(String xpath){
        return driver.findElement(By.xpath(xpath));
    }
    public void fnSelectDropDownValue(ConfigTestRunner configTestRunner, String Value,WebElement element){

    }
    public boolean selectCombobox(WebElement element,ConfigTestRunner configTestRunner,String Value){
        boolean exist;
        setFocusClick(element);
        sendKeysDownClick(element);
        String valueXpath = "//option[text()='"+Value+"']";
        try{
            WebElement item =  getElement(valueXpath);
            item.click();
            configTestRunner.getCommonMethods().sendKeysEnter(element);
            exist = true;
        }catch (Exception e){
            exist = false;
        }
        return exist;
    }

    public boolean selectComboboxcontains(WebElement element,ConfigTestRunner configTestRunner,String Value){
        boolean exist;
        setFocusClick(element);
        sendKeysDownClickEnter(element);
        String valueXpath = "//option[contains(text(),'"+Value+"')]";
        try{
            WebElement item =  getElement(valueXpath);

            exist = true;
        }catch (Exception e){
            exist = false;
        }
        return exist;
    }
    public void selectComboboxcontainsindivisual(WebElement element,ConfigTestRunner configTestRunner,String Value) {
        boolean exist;
        setFocusClick(element);
        sendKeysDownClickEnter(element);
    }
    public String getTodaysDate(ConfigTestRunner configTestRunner,String dateFormat1){
        //Select expirary date
        DateFormat dateFormat = new SimpleDateFormat(dateFormat1);
        Calendar c = Calendar.getInstance();
        try{
            //Setting the date to the given date
            c.setTime(dateFormat.parse(dateFormat.format(new Date())));
        }catch(ParseException e){
            e.printStackTrace();
        }
        String newDate = dateFormat.format(c.getTime());
        return  newDate;
    }
    public boolean selectComboboxSendKeys(WebElement element,ConfigTestRunner configTestRunner,String Value){
        boolean exist;
        configTestRunner.getCommonMethods().setFocus(element,Value);
        String valueXpath = "//option[contains(text(),'"+Value+"')]";
        try{
            WebElement item =  configTestRunner.getCommonMethods().getElement(valueXpath);
            if(item.isDisplayed()) {
                item.click();
                configTestRunner.getCommonMethods().sendKeysEnter(element);
            }
            exist = true;
        }catch (Exception e){
            exist = false;
            e.printStackTrace();
        }
        return exist;
    }
    public boolean selectComboboxLinkSendKeys(WebElement element,ConfigTestRunner configTestRunner,String Value){
        boolean exist;
        configTestRunner.getCommonMethods().setFocus(element,Value);
        String valueXpath = "//a[text()='"+Value+"']";
        try{
            WebElement item =  configTestRunner.getCommonMethods().getElement(valueXpath);
            if(item.isDisplayed()) {
                item.click();
                configTestRunner.getCommonMethods().sendKeysEnter(element);
            }
            exist = true;
        }catch (Exception e){
            exist = false;
            e.printStackTrace();
        }
        return exist;
    }
    public boolean selectComboboxDivSendKeys(WebElement element,ConfigTestRunner configTestRunner,String Value){
        boolean exist;
        configTestRunner.getCommonMethods().setFocus(element,Value);
        String valueXpath = "//div[text()='"+Value+"']";
        try{
            WebElement item =  configTestRunner.getCommonMethods().getElement(valueXpath);
            if(item.isDisplayed()) {
                item.click();
                configTestRunner.getCommonMethods().sendKeysEnter(element);
            }
            exist = true;
        }catch (Exception e){
            exist = false;
            e.printStackTrace();
        }
        return exist;
    }
    public WebElement getDistributionListMember(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//input[@name='AddressRadios' and contains(@title,'" + text + "')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());

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
    public WebElement getPrecontainsWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//pre[contains(text(),'" + text + "')]"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,text+" :field is not present in the application.");
        }
        if (webElement.size() >= 1)
            return webElement.get((webElement.size() - 1));
        else
            return webElement.get(webElement.size());
    }

    public void sendKeysActionClick(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        setImplicitlyWait(20);
        action.doubleClick();
        action.build().perform();
    }

}
