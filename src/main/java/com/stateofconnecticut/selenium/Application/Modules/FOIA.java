package com.stateofconnecticut.selenium.Application.Modules;

import com.aventstack.extentreports.Status;
import com.stateofconnecticut.selenium.config.Configuration;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import com.stateofconnecticut.selenium.utilities.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FOIA extends BaseAction{
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public FOIA(WebDriver driver, Configuration conf){
        super(driver,conf);
    }
    @Override
    public String getFormName() {
        return "FOIA";
    }
    public void fnAddNewFOIA(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Add New FOIA Request"));
        List<String> fields = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("Fields").split(",")));
        fillForm(fields, "PublicComment", configTestRunner);
        configTestRunner.getChildTest().log(Status.PASS,"Fill new submission form - successful");
        //
        WebElement browser1 =getWebElement("PublicComment","filingDescription");
        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor)driver).executeScript(js,browser1);
        browser1.sendKeys("Filing");
        logger.info("Fill new submission form - successfully.");

        WebElement browser =driver.findElement(By.cssSelector("input[type='file']"));
         js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor)driver).executeScript(js,browser);
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
        sleep(300);
        browser.sendKeys(filePath);

        sleep(300);
        try{
        //click on attachment document
        if(getWebElement("MatterFiling","attachDocumentBtton").isEnabled()) {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "MatterFiling","attachDocumentBtton"), Constants.AJAX_TIMEOUT);
            configTestRunner.getCommonMethods().setFocusClick(getWebElement( "MatterFiling","attachDocumentBtton"));
        }
    }catch (Exception e){
        configTestRunner.getChildTest().log(Status.INFO, "Comment field is not displayed or data passed is empty");
    }
        try {
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("PublicComment","documentTypeHeader"), Constants.AJAX_TIMEOUT);
        if (getWebElement("PublicComment","documentTypeHeader").isDisplayed())
            configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
        else
            configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
    }catch (Exception e) {

    }
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("submitButton"),Constants.waitForEleLoad);
        WebElement userEnterAddress = getWebElement("PublicComment","userEnterAddress");
        //click on user enter address field
        try{
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("PublicComment","useSuggestedAddress"),30);
        }catch (Exception e){
            configTestRunner.getCommonMethods().waitAndClick(userEnterAddress,30);
            configTestRunner.getChildTest().log(Status.INFO, "Enter user address pop us is not coming.");
        }
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Matter Confirmation Verification"));
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("confirmationMessage"),30);
            Assert.assertTrue(getWebElement("confirmationMessage").isDisplayed());
            if (getWebElement("confirmationMessage").isDisplayed()) {
                if (!(getWebElement("confirmationMessage").getText().contains("FA"))) {
                    String[] matterno = getWebElement("confirmationMessage").getText().split(" ");
                    configTestRunner.MatterNumber = matterno[matterno.length - 1];
                    configTestRunner.data.setCellData(configTestRunner.MatterNumber, configTestRunner.data.rowValue(configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO", "TestCase")), configTestRunner.data.ColumnValue("Matter_No", "TestCase"));
                    configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully & the matter number is :" + configTestRunner.MatterNumber);
                    try {
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName( "FOIACreated_")));

                    } catch (Exception e) {

                    }
                    logger.info("Matter is created with matter no :" + configTestRunner.MatterNumber);
                }
            }
    }

}
