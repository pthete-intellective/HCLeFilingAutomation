package com.stateofconnecticut.selenium.Application.Modules;

import com.aventstack.extentreports.Status;
import com.stateofconnecticut.selenium.config.Configuration;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import com.stateofconnecticut.selenium.utilities.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
public class LoginAction extends  BaseAction{
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public LoginAction(WebDriver driver, Configuration conf) {
        super(driver, conf);
    }
    @Override
    public String getFormName() {
        return "Login";
    }
    public void fnLoginApplication(WebDriver driver, String  url,String userId, String password, ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Login To The External Verification"));
        driver.get(url);
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("userIdText"),120);
        if (Configuration.login != null) {
            userId = Configuration.login;
        }
        if (Configuration.password != null) {
            password = Configuration.password;
        }

//        getWebElement("userIdText").clear();
        getWebElement("userIdText").sendKeys(userId);
        configTestRunner.getChildTest().log(Status.INFO,"Log with user : "+userId);
        getWebElement("passwordText").clear();
        getWebElement("passwordText").sendKeys(password);
        configTestRunner.getChildTest().log(Status.INFO,"Log with password : "+password);
        configTestRunner.getCommonMethods().setFocusClick(getWebElement("logInButton"));
        configTestRunner.getCommonMethods().setImplicitlyWait(20);
        String securityQuestion = getWebElement("question").getText().trim();
        String answer = configTestRunner.fnAnsSequirityQuestion(securityQuestion);
        configTestRunner.getCommonMethods().setFocus(getWebElement("answer"),answer);
        //Click on submit button
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("submitButton"),40);
        if(getWebElement("PURAHome").isDisplayed()){
            configTestRunner.getChildTest().log(Status.PASS, "Login to the application is successfully.");
        }
        else {
            try {
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                configTestRunner.getChildTest().log(Status.FAIL, "Login to the application is not- successfully" + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

            } catch (Exception e) {

            }
        }
    }
    public void fnLoginInternalApplication(WebDriver driver, String url,String userId, String password, ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Login To The Internal Verification"));
        driver.get(url);
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("InternalLogin","userIdText"),Constants.waitForEleLoad);
        getWebElement("InternalLogin","userIdText").sendKeys(userId);
        configTestRunner.getChildTest().log(Status.INFO,"Log with user : "+userId);
        getWebElement("InternalLogin","passwordText").clear();
        getWebElement("InternalLogin","passwordText").sendKeys(password);
        configTestRunner.getChildTest().log(Status.INFO,"Log with password : "+password);
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("InternalLogin","submitButton"));
        sleep(3000);
        configTestRunner.getCommonMethods().fnWaitForVisibility(configTestRunner.getCommonMethods().getspanWithText(configTestRunner,"Search"),120);
        if(configTestRunner.getCommonMethods().getspanWithText(configTestRunner,"Search").isDisplayed()){
            configTestRunner.getChildTest().log(Status.PASS, "Login to the application is successfully.");
        }
        else {
            try {
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                configTestRunner.getChildTest().log(Status.FAIL, "Login to the application is not- successfully" + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

            } catch (Exception e) {

            }
        }
    }
    public void fnLoghOut(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("LogOut from the external application"));
        sleep(500);
        if(fnWaitForVisibility(getWebElement("logoutbtn"),Constants.waitForEleLoad)){
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("logoutbtn"), Constants.waitForEleLoad);
            try {
                if (fnWaitForVisibility(getWebElement("passwordText"), Constants.waitForEleLoad)) {
                    configTestRunner.getChildTest().log(Status.PASS, "Logout from the external pplication is successfully.");
                }
            }catch (Exception e){
                configTestRunner.getChildTest().log(Status.FAIL, "Logout from the external pplication is not successfully.");
            }
        }
    }

}
