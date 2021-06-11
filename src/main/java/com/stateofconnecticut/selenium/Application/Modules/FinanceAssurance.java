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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FinanceAssurance extends BaseAction {
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public FinanceAssurance(WebDriver driver, Configuration conf){
        super(driver,conf);
    }
    @Override
    public String getFormName() {
        return "FinancialTab";
    }
    public void fnReplacementFA(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Finance Tab Verification For Replacement Of FA"));
        Assert.assertTrue(getWebElement("replacementFileFinanceTab").isDisplayed());
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("replacementFileFinanceTab"), Constants.waitForEleLoad);
        if(getWebElement("searchMatterFinanceTab").isDisplayed()){
            getWebElement("searchMatterFinanceTab").sendKeys(configTestRunner.FAMatterNumber);
            getWebElement("CommentFinanceTab").sendKeys("Comment");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnFinanceTab"),Constants.waitForEleLoad);
            fnWaitForVisibility(getWebElement("TypeOfInstrument"),Constants.waitForEleLoad);
            //select type of instrument
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("TypeOfInstrument"),configTestRunner,configTestRunner.getTestData().get("typeOfInstrument"));
            configTestRunner.getChildTest().log(Status.PASS, "Instrument Type is selected is: "+configTestRunner.getTestData().get("typeOfInstrument"));
            //select instrument number
            configTestRunner.getCommonMethods().setFocus(getWebElement("InstrumentNum"),configTestRunner.getTestData().get("InstrumentNum"));
            //Add the company
            fnAddFinInstitute(configTestRunner);
            //Select the existing company
            configTestRunner.getMatterCreation().fnSelectAddress(configTestRunner);
            //select issued Date
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            WebElement browser =driver.findElement(By.cssSelector("input[name='IssueDate']"));
            String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.click();
//            browser.sendKeys(dateFormat.format(new Date()));
            driver.findElement(By.xpath("//td[contains(@class,'today')]")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,dateFormat.format(new Date()));
            configTestRunner.getChildTest().log(Status.PASS, "Start date selected is: "+dateFormat.format(new Date()));
            //Select expirary date
            Calendar c = Calendar.getInstance();
            try{
                //Setting the date to the given date
                c.setTime(dateFormat.parse(dateFormat.format(new Date())));
            }catch(ParseException e){
                e.printStackTrace();
            }
            //Number of Days to add
            c.add(Calendar.DAY_OF_MONTH, 30);
            //Date after adding the days to the given date
            String newDate = dateFormat.format(c.getTime());
            browser =driver.findElement(By.cssSelector("input[name='ExpirationDate']"));
            js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.sendKeys(newDate);
            int month=Integer.parseInt(newDate.split("/")[0].replace("0",""));
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("selectMonth"),configTestRunner,theMonth((month-1)));
            String day=newDate.split("/")[1].replace("0","");
            driver.findElement(By.xpath("//a[text()='"+day+"']")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,newDate);
            configTestRunner.getChildTest().log(Status.PASS, "Expiry date selected is: "+newDate);
            //select document type
            configTestRunner.getCommonMethods().selectComboboxSendKeys(getWebElement("doctype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
            //select subDocument Type
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("DocumentSubtype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Sub Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
            //select documet
             browser =driver.findElement(By.cssSelector("input[type='file']"));
             js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
            sleep(300);
            browser.sendKeys(filePath);
//            //Select file for upload
//            WebElement element = getWebElement("chooseFileUpload");
//            configTestRunner.getCommonMethods().setFocusClick(element);
//            String filePath = System.getProperty("user.dir") + "\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
//            try {
//                Thread.sleep(300);
//            }catch (Exception e){
//
//            }
//            configTestRunner.getMatterCreation().fnFileUploadFunctionality(configTestRunner,filePath);


            try {
                Thread.sleep(300);
            }catch (Exception e){

            }
            //click on attachment document
            if(getWebElement("uploadButton").isEnabled()) {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "uploadButton"), Constants.AJAX_TIMEOUT);
                configTestRunner.getCommonMethods().setFocusClick(getWebElement( "uploadButton"));
            }
            try {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("MatterFiling","documentTypeHeader"), Constants.AJAX_TIMEOUT);
                if (getWebElement("MatterFiling","documentTypeHeader").isDisplayed())
                    configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
                else
                    configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
            }catch (Exception e) {

            }

        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Replacement Financial Matter Tab verification window is not opened");
    }
    public void fnRenewFA(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Finance Tab Verification For Replacement Of FA"));
        Assert.assertTrue(getWebElement("renewFileFinanceTab").isDisplayed());
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("renewFileFinanceTab"), Constants.waitForEleLoad);
        if(getWebElement("searchMatterFinanceTab").isDisplayed()){
            getWebElement("searchMatterFinanceTab").sendKeys(configTestRunner.FAMatterNumber);
            getWebElement("CommentFinanceTab").sendKeys("Comment");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnFinanceTab"),Constants.waitForEleLoad);
            fnWaitForVisibility(getWebElement("TypeOfInstrument"),Constants.waitForEleLoad);
            //select type of instrument
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("TypeOfInstrument"),configTestRunner,configTestRunner.getTestData().get("typeOfInstrument"));
            configTestRunner.getChildTest().log(Status.PASS, "Instrument Type is selected is: "+configTestRunner.getTestData().get("typeOfInstrument"));
            //select instrument number
            configTestRunner.getCommonMethods().setFocus(getWebElement("InstrumentNum"),configTestRunner.getTestData().get("InstrumentNum"));
            //Add the company
            fnAddFinInstitute(configTestRunner);
            //search the matter no
            fnAddMatterNumbe(configTestRunner,configTestRunner.MatterNumber);
            //Select the existing company
            configTestRunner.getMatterCreation().fnSelectAddress(configTestRunner);
            //select issued Date
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            WebElement browser =driver.findElement(By.cssSelector("input[name='IssueDate']"));
            String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.click();
//            browser.sendKeys(dateFormat.format(new Date()));
            driver.findElement(By.xpath("//td[contains(@class,'today')]")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,dateFormat.format(new Date()));
            configTestRunner.getChildTest().log(Status.PASS, "Start date selected is: "+dateFormat.format(new Date()));
            //Select expirary date
            Calendar c = Calendar.getInstance();
            try{
                //Setting the date to the given date
                c.setTime(dateFormat.parse(dateFormat.format(new Date())));
            }catch(ParseException e){
                e.printStackTrace();
            }
            //Number of Days to add
            c.add(Calendar.DAY_OF_MONTH, 30);
            //Date after adding the days to the given date
            String newDate = dateFormat.format(c.getTime());
            browser =driver.findElement(By.cssSelector("input[name='ExpirationDate']"));
            js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.sendKeys(newDate);
            int month=Integer.parseInt(newDate.split("/")[0].replace("0",""));
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("selectMonth"),configTestRunner,theMonth((month-1)));
            String day=newDate.split("/")[1].replace("0","");
            driver.findElement(By.xpath("//a[text()='"+day+"']")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,newDate);
            configTestRunner.getChildTest().log(Status.PASS, "Expiry date selected is: "+newDate);
            //select document type
            configTestRunner.getCommonMethods().selectComboboxSendKeys(getWebElement("doctype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
            //select subDocument Type
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("DocumentSubtype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Sub Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
            //Select file for upload
//            WebElement element = getWebElement("chooseFileUpload");
//            configTestRunner.getCommonMethods().setFocusClick(element);
//            String filePath = System.getProperty("user.dir") + "\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
//            try {
//                Thread.sleep(300);
//            }catch (Exception e){
//
//            }
//            configTestRunner.getMatterCreation().fnFileUploadFunctionality(configTestRunner,filePath);
//
//
//            try {
//                Thread.sleep(300);
//            }catch (Exception e){
//
//            }
            //select documet
             browser =driver.findElement(By.cssSelector("input[type='file']"));
             js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
            sleep(300);
            browser.sendKeys(filePath);
            //click on attachment document
            if(getWebElement("uploadButton").isEnabled()) {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "uploadButton"), Constants.AJAX_TIMEOUT);
                configTestRunner.getCommonMethods().setFocusClick(getWebElement( "uploadButton"));
            }
            try {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("MatterFiling","documentTypeHeader"), Constants.AJAX_TIMEOUT);
                if (getWebElement("MatterFiling","documentTypeHeader").isDisplayed())
                    configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
                else
                    configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
            }catch (Exception e) {

            }

        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Replacement Financial Matter Tab verification window is not opened");
    }

    public void fnCreateNewFA(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Finance Tab Verification"));
        //select New FA
        Assert.assertTrue(getWebElement("newFileFinanceTab").isDisplayed());
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("newFileFinanceTab"), Constants.waitForEleLoad);
        if(getWebElement("TypeOfInstrument").isDisplayed()){
            try {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("RevenueReporNo"), 10);
                configTestRunner.getCommonMethods().setFocus(getWebElement("estimatedGrossReceipt"), configTestRunner.getTestData().get("estimatedGrossReceiptAmt"));
            }catch (Exception ex){

            }
            //search the matter no
            fnAddMatterNumbe(configTestRunner,configTestRunner.MatterNumber);
            //select type of instrument
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("TypeOfInstrument"),configTestRunner,configTestRunner.getTestData().get("typeOfInstrument"));
            configTestRunner.getChildTest().log(Status.PASS, "Instrument Type is selected is: "+configTestRunner.getTestData().get("typeOfInstrument"));
            //select instrument number
            configTestRunner.getCommonMethods().setFocus(getWebElement("InstrumentNum"),configTestRunner.getTestData().get("InstrumentNum"));
            //Add the company
            fnAddFinInstitute(configTestRunner);
            //Select the existing company
            configTestRunner.getMatterCreation().fnSelectAddress(configTestRunner);
            //select issued Date
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            WebElement browser =driver.findElement(By.cssSelector("input[name='IssueDate']"));
            String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.click();
//            browser.sendKeys(dateFormat.format(new Date()));
            driver.findElement(By.xpath("//td[contains(@class,'today')]")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,dateFormat.format(new Date()));
            configTestRunner.getChildTest().log(Status.PASS, "Start date selected is: "+dateFormat.format(new Date()));
            //Select expirary date
            Calendar c = Calendar.getInstance();
            try{
                //Setting the date to the given date
                c.setTime(dateFormat.parse(dateFormat.format(new Date())));
            }catch(ParseException e){
                e.printStackTrace();
            }
            //Number of Days to add
            c.add(Calendar.DAY_OF_MONTH, 30);
            //Date after adding the days to the given date
            String newDate = dateFormat.format(c.getTime());
             browser =driver.findElement(By.cssSelector("input[name='ExpirationDate']"));
             js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            sleep(400);
            browser.sendKeys(newDate);
            int month=Integer.parseInt(newDate.split("/")[0].replace("0",""));
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("selectMonth"),configTestRunner,theMonth((month-1)));
            String day=newDate.split("/")[1].replace("0","");
            driver.findElement(By.xpath("//a[text()='"+day+"']")).click();
//            configTestRunner.getCommonMethods().setFocus(browser,newDate);
            configTestRunner.getChildTest().log(Status.PASS, "Expiry date selected is: "+newDate);
            //select document type
            configTestRunner.getCommonMethods().selectComboboxSendKeys(getWebElement("doctype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
            //select subDocument Type
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("DocumentSubtype"),configTestRunner,configTestRunner.getTestData().get("finance_DocType"));
            configTestRunner.getChildTest().log(Status.PASS, "Document Sub Type selected is: "+configTestRunner.getTestData().get("finance_DocType"));
//            //Select file for upload
//            WebElement element = getWebElement("chooseFileUpload");
//            configTestRunner.getCommonMethods().setFocusClick(element);
//            String filePath = System.getProperty("user.dir") + "\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
//            try {
//                Thread.sleep(300);
//            }catch (Exception e){
//
//            }
//            configTestRunner.getMatterCreation().fnFileUploadFunctionality(configTestRunner,filePath);
//
//
//            try {
//                Thread.sleep(300);
//            }catch (Exception e){
//
//            }
            //select documet
             browser =driver.findElement(By.cssSelector("input[type='file']"));
             js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
            sleep(300);
            browser.sendKeys(filePath);
            //click on attachment document
            if(getWebElement("uploadButton").isEnabled()) {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "uploadButton"), Constants.AJAX_TIMEOUT);
                configTestRunner.getCommonMethods().setFocusClick(getWebElement( "uploadButton"));
            }
            try {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("MatterFiling","documentTypeHeader"), Constants.AJAX_TIMEOUT);
                if (getWebElement("MatterFiling","documentTypeHeader").isDisplayed())
                    configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
                else
                    configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
            }catch (Exception e) {

            }

        }else
            configTestRunner.getChildTest().log(Status.FAIL,"File New Financial Assurance Tab verification window is not opened");
    }

    public void fnAddFinInstitute(ConfigTestRunner configTestRunner)
    {
        try {
            //click on ad button
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("FinInstNameAdd"), 20);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            //click on ad button
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("FinInstNameAddinternal"), 10);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(getWebElement("Add_InstituteClientType").isDisplayed()){
            //Institute Client Type
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("Add_InstituteClientType"),configTestRunner,configTestRunner.getTestData().get("institutateName"));
            //Indivisual Name
            sleep(1000);
            configTestRunner.getCommonMethods().selectComboboxLinkSendKeys(getWebElement("Add_CompanyName"),configTestRunner,configTestRunner.getTestData().get("financeTabAdd_CompanyName"));
            configTestRunner.getChildTest().log(Status.PASS,"Finance Institute name is selected successfully.");
            //click on next button
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("MatterFiling","next"),Constants.waitForEleLoad);
        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Select the FA company window is not selected successfully.");
        logger.info("Finance tab new finance assurance is successful.");
    }
    public void fnAddFinInstitute_Internal(ConfigTestRunner configTestRunner)
    {
        try {
            //click on ad button
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("FinInstNameAddinternal"), Constants.waitForEleLoad);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(getWebElement("Add_InstituteClientType").isDisplayed()){
            //Institute Client Type
            configTestRunner.getCommonMethods().selectCombobox(getWebElement("Add_InstituteClientType"),configTestRunner,configTestRunner.getTestData().get("institutateName"));
            //Indivisual Name
            sleep(1000);
            configTestRunner.getCommonMethods().selectComboboxLinkSendKeys(getWebElement("Add_CompanyName"),configTestRunner,configTestRunner.getTestData().get("financeTabAdd_CompanyName"));
            configTestRunner.getChildTest().log(Status.PASS,"Institute name is selected successfully.");
            //click on next button
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("MatterFiling","next"),Constants.waitForEleLoad);
        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Select the  company window is not selected successfully.");
        logger.info("Institute is added successfuly.");
    }
    public void clickMatterLink(ConfigTestRunner configTestRunner,String selectmatterlink,String puraLink){
        configTestRunner.getCommonMethods().fnMoveToElement(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,puraLink));
        WebElement newMatterLink =driver.findElement(By.xpath("//a[text() = 'Add New' and contains(@href,'AddNewFA')]"));
        configTestRunner.getCommonMethods().waitAndClick(newMatterLink,30);
        configTestRunner.getChildTest().log(Status.INFO,"click on "+configTestRunner.getTestData().get("selectMatter")+ " link successfully." );
    }
    public void fnAddMatterNumbe(ConfigTestRunner configTestRunner, String matterno){
        try {
            fnWaitForVisibility(getWebElement("addMatterDocketbtn"), 10);
            if (getWebElement("addMatterDocketbtn").isDisplayed()) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("addMatterDocketbtn"), Constants.waitForEleLoad);
                sleep(1000);
                if (fnWaitForVisibility(getWebElement("addMatterDocketbtnMatterNo"), Constants.waitForEleLoad)) {
                    getWebElement("addMatterDocketbtnMatterNo").sendKeys(matterno);
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("addMatterDocketbtnMatterbtnSearch"), Constants.waitForEleLoad);
                    if (driver.findElement(By.xpath("//td[text()='" + matterno + "']")).isDisplayed()) {
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("addMatterDcktSelectRecord"), Constants.waitForEleLoad);
                        //Click on select button
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("MatterDocketSelectButton"), Constants.waitForEleLoad);
                        //select the yes or no radio button
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("addMatterRevenueReportNo"), Constants.waitForEleLoad);
                        //Enter receipt amount
                        getWebElement("estimatedReceiptAmt").sendKeys("1234");
                        //set the receipt amount
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnSetEstimatedAmount"), Constants.waitForEleLoad);

                    } else
                        configTestRunner.getChildTest().log(Status.FAIL, "Matter is not search successfully");

                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "FA search matter number screen is displayed");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Add button to search matter is not coming.");
        }catch (Exception e){

        }
    }
    public void fnMatterConfirmationFA(ConfigTestRunner configTestRunner){
        //click on submit button
        try{
            if(getWebElement("submitbtn").isDisplayed())
                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("submitbtn"));

        }catch (Exception e){
        }

        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Matter Confirmation For FA Verification"));

        try {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("MatterFiling","confirmationMessage"),30);
            if (getWebElement("MatterFiling","confirmationMessage").isDisplayed()) {
                    String[] matterno = getWebElement("MatterFiling","confirmationMessage").getText().split(" ");
                    configTestRunner.MatterNumber = matterno[matterno.length - 1];
                    configTestRunner.data.setCellData(configTestRunner.MatterNumber, configTestRunner.data.rowValue(configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO", "TestCase")), configTestRunner.data.ColumnValue("FA_Matter", "TestCase"));
                    configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully & the matter number is :" + configTestRunner.MatterNumber);
                    try {
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                    } catch (Exception e) {
                    }
                    logger.info("Matter is created with matter no :" + configTestRunner.MatterNumber);

            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Matter is not created successfully & the matter number is not created:");
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL, "Matter is not created successfully & the matter number is not created :");
            try {
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

            } catch (Exception ex) {
            }

        }
        configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,"PURA E-Filing"),Constants.waitForEleLoad);
    }
}
