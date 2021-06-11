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

public class PublicComment extends BaseAction{
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public PublicComment(WebDriver driver, Configuration conf){
        super(driver,conf);
    }
    @Override
    public String getFormName() {
        return "PublicComment";
    }
    public void fnFillComment(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Public Comment Verification"));
        WebElement Fname = getWebElement("firstName");
        WebElement Lname = getWebElement("lastName");
        WebElement Address1 = getWebElement("address1");
        WebElement Address2 = getWebElement("address2");
        WebElement city = getWebElement("city");
        WebElement zipCode = getWebElement("zipCode");
        WebElement zip4 = getWebElement("zip4");
        WebElement Country = getWebElement("Country");
        WebElement stateProvience = getWebElement("stateProvience");
        WebElement phoneNumber = getWebElement("phoneNumber");
        WebElement Extension = getWebElement("Extension");
        WebElement txtEMail = getWebElement("txtEMail");
        WebElement companyEntity = getWebElement("companyEntity");
        WebElement comment = getWebElement("comment");
        WebElement btnPostComment = getWebElement("btnPostComment");
        WebElement userEnterAddress = getWebElement("userEnterAddress");
        //first name
        try{
            if(Fname.isDisplayed() &&  (!(configTestRunner.getTestData().get("firstName").isEmpty()))){
                    Fname.sendKeys(configTestRunner.getTestData().get("firstName"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("firstName")+" this value is entered in first name field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "First Name field is not displayed or data passed is empty");
        }
        //last name
        try{
            if(Lname.isDisplayed() &&  (!(configTestRunner.getTestData().get("lastName").isEmpty()))){
                Lname.sendKeys(configTestRunner.getTestData().get("lastName"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("lastName")+" this value is entered in last name field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Last Name field is not displayed or data passed is empty");
        }
        //Address1
        try{
            if(Address1.isDisplayed() &&  (!(configTestRunner.getTestData().get("address1").isEmpty()))){
                Address1.sendKeys(configTestRunner.getTestData().get("address1"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("address1")+" this value is entered in Address1 field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Address1 field is not displayed or data passed is empty");
        }
        //Address2
        try{
            if(Address2.isDisplayed() &&  (!(configTestRunner.getTestData().get("address2").isEmpty()))){
                Address2.sendKeys(configTestRunner.getTestData().get("address2"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("address2")+" this value is entered in Address2 field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Address2 field is not displayed or data passed is empty");
        }
        //city
        try{
            if(city.isDisplayed() &&  (!(configTestRunner.getTestData().get("city").isEmpty()))){
                city.sendKeys(configTestRunner.getTestData().get("city"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("city")+" this value is entered in city field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "City field is not displayed or data passed is empty");
        }
        //Zip Code
        try{
            if(zipCode.isDisplayed() &&  (!(configTestRunner.getTestData().get("zipCode").isEmpty()))){
                zipCode.sendKeys(configTestRunner.getTestData().get("zipCode"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("zipCode")+" this value is entered in zip code field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Zip Code field is not displayed or data passed is empty");
        }
        //zip4
        try{
            if(zip4.isDisplayed() &&  (!(configTestRunner.getTestData().get("zip4").isEmpty()))){
                zip4.sendKeys(configTestRunner.getTestData().get("zip4"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("zip4")+" this value is entered in zip4 field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Zip4  field is not displayed or data passed is empty");
        }
        //Country
        try{
            if(Country.isDisplayed() &&  (!(configTestRunner.getTestData().get("Country").isEmpty()))){
                configTestRunner.getCommonMethods().selectComboboxSendKeys(Country,configTestRunner,configTestRunner.getTestData().get("Country"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("Country")+" this value is entered in Country field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Country field is not displayed or data passed is empty");
        }
        //stateProvience
        try{
            if(stateProvience.isDisplayed() &&  (!(configTestRunner.getTestData().get("stateProvience").isEmpty()))){
                configTestRunner.getCommonMethods().selectComboboxSendKeys(stateProvience,configTestRunner,configTestRunner.getTestData().get("stateProvience"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("stateProvience")+" this value is entered in stateProvience field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "stateProvience field is not displayed or data passed is empty");
        }
        //phoneNumber
        try{
            if(phoneNumber.isDisplayed() &&  (!(configTestRunner.getTestData().get("phoneNumber").isEmpty()))){
                phoneNumber.sendKeys(configTestRunner.getTestData().get("phoneNumber"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("phoneNumber")+" this value is entered in phone number field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "phone number field is not displayed or data passed is empty");
        }
        //Extension
        try{
            if(Extension.isDisplayed() &&  (!(configTestRunner.getTestData().get("Extension").isEmpty()))){
                Extension.sendKeys(configTestRunner.getTestData().get("Extension"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("Extension")+" this value is entered in Extension field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Extension field is not displayed or data passed is empty");
        }
        //txtEMail
        try{
            if(txtEMail.isDisplayed() &&  (!(configTestRunner.getTestData().get("txtEMail").isEmpty()))){
                txtEMail.sendKeys(configTestRunner.getTestData().get("txtEMail"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("txtEMail")+" this value is entered in email field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Email field is not displayed or data passed is empty");
        }
        //companyEntity
        try{
            if(companyEntity.isDisplayed() &&  (!(configTestRunner.getTestData().get("companyEntity").isEmpty()))){
                companyEntity.sendKeys(configTestRunner.getTestData().get("companyEntity"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("companyEntity")+" this value is entered in company entity field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Company Entity field is not displayed or data passed is empty");
        }
        //comment
        try{
            if(comment.isDisplayed() &&  (!(configTestRunner.getTestData().get("comment").isEmpty()))){
                comment.sendKeys(configTestRunner.getTestData().get("comment"));
                configTestRunner.getChildTest().log(Status.PASS,configTestRunner.getTestData().get("comment")+" this value is entered in comment field");
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Comment field is not displayed or data passed is empty");
        }
        //upload document
        try{
            //select documet
//            WebElement element = getWebElement("MatterFiling","chooseFileUpload");
////        configTestRunner.getCommonMethods().setFocusWithoutClick(element,"C:\\Users\\DhageJ\\IdeaProjects\\StateOfCT.stageExternal.com\\UploadDocument\\Image.jpg");
//            configTestRunner.getCommonMethods().setFocusClick(element);
//            String filePath = System.getProperty("user.dir") + "\\UploadDocument\\Image.jpg";
//            sleep(300);
//            fnFileUploadFunctionality(configTestRunner,filePath);
            //select documet
            WebElement browser =driver.findElement(By.cssSelector("input[type='file']"));
            String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
            ((JavascriptExecutor)driver).executeScript(js,browser);
            String filePath = System.getProperty("user.dir") + "\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
            sleep(300);
            browser.sendKeys(filePath);

            sleep(300);
            //click on attachment document
            if(getWebElement("MatterFiling","attachDocumentBtton").isEnabled()) {
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "MatterFiling","attachDocumentBtton"), Constants.AJAX_TIMEOUT);
                configTestRunner.getCommonMethods().setFocusClick(getWebElement( "MatterFiling","attachDocumentBtton"));
            }
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.INFO, "Comment field is not displayed or data passed is empty");
        }
        try {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("documentTypeHeader"), Constants.AJAX_TIMEOUT);
            if (getWebElement("documentTypeHeader").isDisplayed())
                configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
            else
                configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
        }catch (Exception e) {

        }
        //Click on edit post button
        try{
            if(btnPostComment.isDisplayed())
                configTestRunner.getCommonMethods().waitAndClick(btnPostComment,Constants.waitForEleLoad);
        }catch (Exception e){

        }
        //click on user enter address field
        try{
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("useSuggestedAddress"),30);
        }catch (Exception e){
            configTestRunner.getCommonMethods().waitAndClick(userEnterAddress,30);
            configTestRunner.getChildTest().log(Status.INFO, "Enter user address pop us is not coming.");
        }
        String text ="Thank you for submitting public comments in Matter/Docket # "+configTestRunner.MatterNumber;
        if(configTestRunner.getCommonMethods().getdivContainsWithText(configTestRunner,text).isDisplayed()){
            configTestRunner.getChildTest().log(Status.PASS,"Public comment is submitted successfully");
        }
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("doneButton"),Constants.waitForEleLoad);
        try {
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

        } catch (Exception e) {
        }
    }
}
