package com.stateofconnecticut.selenium.Application.Modules;

import com.aventstack.extentreports.Status;
import com.stateofconnecticut.selenium.config.Configuration;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import com.stateofconnecticut.selenium.utilities.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class MatterCreationAction extends BaseAction {
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public MatterCreationAction(WebDriver driver,Configuration conf){
        super(driver,conf);
    }
    @Override
    public String getFormName() {
        return "MatterFiling";
    }
    public void fnMatterCreation(ConfigTestRunner configTestRunner,String CaseID){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("New Matter Creation Verification"));
        switch (CaseID){
            case "TC0001":
                    //select PURA link
                    selectPUAR(configTestRunner);
                    //click on New MatterLink
//                    clickMatterLink(configTestRunner);
                    //select industry Utility
                    fnselectIndustryUtility(configTestRunner);
                    //Select MatterType
                    fnSelectMatterType(configTestRunner);
                    //Select Subscribe Aggrement
                    fnSubscribeAggrement(configTestRunner);
                    //select address
                    fnSelectAddress(configTestRunner);
                    //Add NewContact number
                    fnAddNewPhoneNo(configTestRunner);
                    //select existing contact person
                    fnselectExistingContactPerson(configTestRunner);
                    //Select On Behalf Of
                    fnOnBehlfOf(configTestRunner);
                    //Select Partining to
                    fnpartiningTo(configTestRunner,configTestRunner.getTestData().get("pertainingTo"));
                    //Select same company
                    fnSameCompanySelect(configTestRunner);
                    //Add Another partining to
                    fnpartiningTo(configTestRunner,"No");
                    try{
                        Thread.sleep(60);
                    }catch (Exception e){

                    }
                    //select distributon member
                    fnselectDistributionliatmember(configTestRunner);
                    //configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("next"));
                    fnMatterTitleGenerated(configTestRunner);
                    fnselectreviewerSubmitter(configTestRunner);

                    fnDescFileSelect(configTestRunner);

                    //click on submit button
                    configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("submitButton"));
                    configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Matter Confirmation Verification"));
                    configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("confirmationMessage"),Constants.waitForEleLoad);
                    if(getWebElement("confirmationMessage").isDisplayed()){
                        if(!(getWebElement("confirmationMessage").getText().contains("FA"))){
                            String [] matterno =getWebElement("confirmationMessage").getText().split(" ");
                            configTestRunner.MatterNumber =matterno[matterno.length-1];
                            configTestRunner.data.setCellData(configTestRunner.MatterNumber,configTestRunner.data.rowValue( configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO","TestCase")),configTestRunner.data.ColumnValue("Matter_No","TestCase"));
                            configTestRunner.getChildTest().log(Status.PASS,"Matter is created successfully & the matter number is :"+configTestRunner.MatterNumber);
                            try{
                            String name = new Object() {
                            }.getClass().getEnclosingMethod().getName();
                            configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                             }catch (Exception e) {
                            }
                        }else{
                            String [] matterno =getWebElement("confirmationMessage").getText().split(" ");
                            configTestRunner.FAMatterNumber =matterno[matterno.length-1];
                            configTestRunner.data.setCellData(configTestRunner.FAMatterNumber,configTestRunner.data.rowValue( configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO","TestCase")),configTestRunner.data.ColumnValue("FA_Matter","TestCase"));
                            configTestRunner.MatterNumber =matterno[matterno.length-5];
                            configTestRunner.data.setCellData(configTestRunner.MatterNumber,configTestRunner.data.rowValue( configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO","TestCase")),configTestRunner.data.ColumnValue("Matter_No","TestCase"));
                        }
                    }else
                        configTestRunner.getChildTest().log(Status.FAIL,"Matter is not created successfully & the matter number is :");
                break;

            case "TC0002":
                        selectPUAR(configTestRunner);
                        //Select the existing matter filing
                        configTestRunner.getCommonMethods().fnMoveToElement(getWebElement("matterFillingLink"));
                        configTestRunner.getChildTest().log(Status.INFO,"click on Existing matter Filing link successfully");
                        //click on New MatterLink
//                        clickMatterLink(configTestRunner,);
                        //Search the existing matter type
                        fnSearchExistingMatter(configTestRunner,configTestRunner.MatterNumber);

                        //Select Subscribe aggrement
                        fnSubscribeAggrement(configTestRunner);
                        //click on next button
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("next"),Constants.waitForEleLoad);
                        //select address
                        fnSelectAddress(configTestRunner);
                        //Click on Next button
                        Assert.assertTrue(getWebElement("nextButtonNew").isEnabled());
                        configTestRunner.getCommonMethods().setFocusdblClick(getWebElement("nextButtonNew"));

                break;
            default:
                logger.info("No test case is selected for execution.");

        }
    }
    public boolean fnMatterInformation(ConfigTestRunner configTestRunner){
        boolean returnFlg = false;
        fnWaitForVisibility(getWebElement("matterInformation"),240);
        if(getWebElement("matterInformation").isDisplayed()) {
            configTestRunner.getChildTest().log(Status.PASS, "Matter Information window is opened successfully which contain matter information.");
        }else{
            configTestRunner.getChildTest().log(Status.FAIL, "Matter Information window is not opened successfully which contain matter information.");
        }
        try{
            if(configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("nextButton"),10));
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"),Constants.waitForEleLoad);
            if (getWebElement("FinancialTab","newFileFinanceTab").isDisplayed()) {
                returnFlg = true;
            }

        }catch (Exception e){
            returnFlg=false;
        }

        return returnFlg;
    }
    public void fnMatterTitleGenerated(ConfigTestRunner configTestRunner){
        try{
            fnWaitForVisibility(getWebElement("matterTitle"),10);
            configTestRunner.getCommonMethods().setFocus(getWebElement("matterTitle"),configTestRunner.getTestData().get("matterTitle"));
        }catch (Exception e){

        }
        if(configTestRunner.getCommonMethods().getspanWithText(configTestRunner,configTestRunner.getTestData().get("fieldBy")).isEnabled()){
            configTestRunner.getChildTest().log(Status.PASS,"New Matter Filing window is open & the matter is filled by: "+configTestRunner.getTestData().get("name"));
        }else
            configTestRunner.getChildTest().log(Status.PASS,"New Matter Filing window is not open");
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("nextButton"));
    }
    public void clickMatterLink(ConfigTestRunner configTestRunner,String selectmatterlink,String puraLink){
        fnWaitForVisibility(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,puraLink),Constants.waitForEleLoad);
        configTestRunner.getCommonMethods().fnMoveToElement(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,puraLink));
        WebElement newMatterLink =configTestRunner.getCommonMethods().getlinkWithTextfirstele(configTestRunner,selectmatterlink);
        configTestRunner.getCommonMethods().waitAndClick(newMatterLink,30);
        configTestRunner.getChildTest().log(Status.INFO,"click on "+configTestRunner.getTestData().get("selectMatter")+ " link successfully." );
    }
    public void fnselectIndustryUtility(ConfigTestRunner configTestRunner){
        if(configTestRunner.getTestCase().get("ApplicationType").contains("PURA")) {
            WebElement industryType = getWebElement("industryUtility");
            configTestRunner.getCommonMethods().selectComboboxSendKeys(industryType, configTestRunner, configTestRunner.getTestData().get("industryType"));
            //click on Next button
            configTestRunner.getCommonMethods().setFocusClick(getWebElement("nextButton"));
        }
    }
    public void fnSelectMatterType(ConfigTestRunner configTestRunner){
        WebElement matterType = getWebElement("MatterType");
        configTestRunner.getCommonMethods().fnWaitForVisibility(matterType, Constants.AJAX_TIMEOUT);
        if(matterType.isDisplayed()) {
            configTestRunner.getCommonMethods().selectComboboxSendKeys(matterType, configTestRunner, configTestRunner.getTestData().get("matterType"));
            configTestRunner.getChildTest().log(Status.INFO, "MatteyType selected is :" + configTestRunner.getTestData().get("matterType"));
            configTestRunner.getCommonMethods().selectComboboxSendKeys(getWebElement("MatterSubType"), configTestRunner, configTestRunner.getTestData().get("MatterSubType"));
            configTestRunner.getChildTest().log(Status.INFO, "MatteySubType selected is :" + configTestRunner.getTestData().get("MatterSubType"));

            String subType = getWebElement( "MatterSubType").getText();
            if (!(subType.equals(null)))
                configTestRunner.getChildTest().log(Status.PASS, "Matter SubType is defaulted with value :" + subType + " after selecting matter type");
            else
                configTestRunner.getChildTest().log(Status.FAIL, "Matter SubType is not defaulted with value :" + subType + " after selecting matter type");
        }else
            configTestRunner.getChildTest().log(Status.FAIL, "New MAtter Filing window is not opened succesfully.");
        //click on Next Button
        configTestRunner.getCommonMethods().setFocusClick(getWebElement("nextButton"));
        if(getWebElement("associatedFiling").isDisplayed())
            configTestRunner.getChildTest().log(Status.PASS,"Companies/Entities/Individuals Associated to this Filing window is opened successfully." );
        else
            configTestRunner.getChildTest().log(Status.FAIL,"Companies/Entities/Individuals Associated to this Filing window is not opened successfully." );
        //click on Next Button
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("nextButtonNew"));
    }
    public void fnSubscribeAggrement(ConfigTestRunner configTestRunner){
        try {
            WebElement subsAggrement = getWebElement("SubsAggrement");
            configTestRunner.getCommonMethods().fnWaitForVisibility(subsAggrement, Constants.AJAX_TIMEOUT);
            if (subsAggrement.isDisplayed()) {
                configTestRunner.getCommonMethods().selectComboboxSendKeys(subsAggrement, configTestRunner, configTestRunner.getTestData().get("subscribeAggrement"));
                configTestRunner.getChildTest().log(Status.INFO, "Subscriber Agreement selected is :" + configTestRunner.getTestData().get("subscribeAggrement"));
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Search for Company/Entity/Individual window is not opened successfully.");
            //Click on Next button
            configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("nextButtonNew"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void fnSelectAddress(ConfigTestRunner configTestRunner){
        try {
            WebElement addressRadioButton = getWebElement("addressRadioButton");
            configTestRunner.getCommonMethods().fnWaitForVisibility(addressRadioButton, Constants.AJAX_TIMEOUT);
            if (addressRadioButton.isDisplayed()) {
                configTestRunner.getCommonMethods().waitAndClick(addressRadioButton, 30);
                configTestRunner.getChildTest().log(Status.PASS, "Existing Address is selected successfully.");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Select or Add Address window is not opened successfully.");
        }catch (Exception e){
            WebElement addnewButton = getWebElement("addnewButton");
            if(addnewButton.isDisplayed()){
                configTestRunner.getCommonMethods().sendKeysEnter(addnewButton);
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("addressline1"),Constants.AJAX_TIMEOUT);
                Assert.assertTrue(getWebElement("addressline1").isDisplayed());
                configTestRunner.getCommonMethods().setFocus(getWebElement("addressline1"),configTestRunner.getTestData().get("addressLine1"));
                //enter city
                configTestRunner.getCommonMethods().setFocus(getWebElement("addressline1City"),configTestRunner.getTestData().get("addressCity"));
                //enter postal code
                configTestRunner.getCommonMethods().setFocus(getWebElement("addressline1ZipCode"),configTestRunner.getTestData().get("addressPostCode"));
                //enter postal zip 4code
                configTestRunner.getCommonMethods().setFocus(getWebElement("addressline1Zipext"),configTestRunner.getTestData().get("addressZipCode"));
                //Click on Next button
                Assert.assertTrue(getWebElement("nextButtonNew").isEnabled());
                configTestRunner.getCommonMethods().setFocusdblClick(getWebElement("nextButtonNew"));
                //click on user enter address field
                try{
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("PublicComment","useSuggestedAddress"),30);
                }catch (Exception ie){
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("PublicComment","userRefinerAddress"),30);
                    configTestRunner.getChildTest().log(Status.INFO, "Enter user address pop us is not coming.");
                }

            }
        }
        //Click on Next button
        Assert.assertTrue(getWebElement("nextButtonNew").isEnabled());
        try {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonNew"),10);
        }catch (Exception e){
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonnew"), 10);
        }
    }
    public void fnAddNewPhoneNo(ConfigTestRunner configTestRunner){

//        configTestRunner.getCommonMethods().setImplicitlyWait(200);
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("phoneNumberHeader"),60);
        WebElement addnewButton = getWebElement("addnewButton");
        if(addnewButton.isDisplayed()){
//            configTestRunner.getCommonMethods().sendKeysEnter(addnewButton);
            configTestRunner.getCommonMethods().waitAndClick(addnewButton,30);
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("phoneNumber"),Constants.AJAX_TIMEOUT);
            Assert.assertTrue(getWebElement("phoneNumber").isDisplayed());
            getWebElement("phoneNumber").sendKeys(configTestRunner.getTestData().get("businessPhone"));
//            configTestRunner.getCommonMethods().setFocus(getWebElement("phoneNumber"),configTestRunner.getTestData().get("businessPhone"));
            //enter extension
            getWebElement("phoneExtension").sendKeys(configTestRunner.getTestData().get("phoneExt"));
//            configTestRunner.getCommonMethods().setFocus(getWebElement("phoneExtension"),configTestRunner.getTestData().get("phoneExt"));

            //Click on Next button
            Assert.assertTrue(getWebElement("nextButtonNew").isEnabled());
//            configTestRunner.getCommonMethods().setFocusdblClick(getWebElement("nextButtonNew"));
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonNew"),10);

        }
        else
            configTestRunner.getChildTest().log(Status.FAIL, "Select or Add Phone Number window is not opened successfully.");
    }
    public void fnselectExistingContactPerson(ConfigTestRunner configTestRunner){
        if(!(configTestRunner.getTestData().get("SelectContactPerson").equals("Yes"))) {
            WebElement contactPerson = getWebElement("selectPerson");
            if (contactPerson.isDisplayed()) {
                configTestRunner.getCommonMethods().waitAndClick(contactPerson, 60);
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Select Contact person window is not opened successfully.");
        }else{
            WebElement addnewButton = getWebElement("addnewButton");
            if(addnewButton.isDisplayed()){
                configTestRunner.getCommonMethods().sendKeysEnter(addnewButton);
                configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("name"),Constants.AJAX_TIMEOUT);
                Assert.assertTrue(getWebElement("name").isDisplayed());
                configTestRunner.getCommonMethods().setFocus(getWebElement("name"),configTestRunner.getTestData().get("name"));
                //enter email id
                configTestRunner.getCommonMethods().setFocus(getWebElement("email"),configTestRunner.getTestData().get("emailId"));
                ;

            }
            else
                configTestRunner.getChildTest().log(Status.FAIL, "Add new person window is not opened successfully.");
        }
        //Click on Next button
        Assert.assertTrue(getWebElement("nextButtonNew").isEnabled());
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonNew"),20);
    }
    public void fnOnBehlfOf(ConfigTestRunner configTestRunner)  {
        WebElement onbehalfOf =getWebElement("onBehalfOf");
        configTestRunner.getCommonMethods().fnWaitForVisibility(onbehalfOf, Constants.AJAX_TIMEOUT);
        if(onbehalfOf.isDisplayed()){
            configTestRunner.getCommonMethods().selectCombobox(onbehalfOf, configTestRunner, configTestRunner.getTestData().get("onBehalfOf"));
            configTestRunner.getChildTest().log(Status.PASS, "On Behalf Of is selected successfully & the Value is: ."+configTestRunner.getTestData().get("onBehalfOf"));
        }
        else
            configTestRunner.getChildTest().log(Status.FAIL, "On Behalf Of window is not opened successfully.");
        try {
            Thread.sleep(30);
        }catch (Exception e){

        }
    }
    public void fnpartiningTo(ConfigTestRunner configTestRunner, String Value){
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("PertainingTo"), Constants.AJAX_TIMEOUT);
        WebElement partingTo =getWebElement("PertainingTo");
        if(partingTo.isDisplayed()){
            configTestRunner.getCommonMethods().selectCombobox(partingTo, configTestRunner, Value);
            configTestRunner.getChildTest().log(Status.PASS, "Partining To is selected successfully & the Value is: ."+Value);
        }
        else
            configTestRunner.getChildTest().log(Status.FAIL, "Add Affiliation/Add Partining to window is not opened successfully.");
    }
    public void fnSameCompanySelect(ConfigTestRunner configTestRunner){
        WebElement company =getWebElement("SameAffiliationList");
        if(company.isDisplayed()){
            configTestRunner.getCommonMethods().selectComboboxcontainsindivisual(company, configTestRunner, configTestRunner.getTestData().get("subscribeAggrement"));
            configTestRunner.getChildTest().log(Status.PASS, "Same company is selected successfully & the Value is: ."+getWebElement("SameAffiliationList").getText());
        }
        else
            configTestRunner.getChildTest().log(Status.FAIL, "Same Company/Entity/Individual as window is not opened successfully.");
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("nextButtonNewAffation"));
        if(getWebElement("selectedMatterWindow").isDisplayed())
            configTestRunner.getChildTest().log(Status.PASS,"Edit Affiliation to this Filing window is opened successfully." );
        else
            configTestRunner.getChildTest().log(Status.FAIL,"Edit Affiliation to this Filing window is not opened successfully." );
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonNew"),Constants.waitForEleLoad);
    }
    public void fnselectDistributionliatmember(ConfigTestRunner configTestRunner) {
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("next"));
        try {
            WebElement disListMember = configTestRunner.getCommonMethods().getDistributionListMember(configTestRunner, configTestRunner.getTestData().get("name"));
            if (disListMember.isDisplayed()) {
                configTestRunner.getCommonMethods().setFocusClick(disListMember);
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Distribution List Members(Service/Participant) window is not opened successfully.");
            configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("next"));
//        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("summaryResult"),Constants.AJAX_TIMEOUT);
//        if(getWebElement("summaryResult").isDisplayed())
//            configTestRunner.getChildTest().log(Status.PASS,"Companies/Entities/Individuals Associated to this Filing summary window is opened successfully." );
//        else
//            configTestRunner.getChildTest().log(Status.FAIL,"Companies/Entities/Individuals Associated to this Filing summary window is not opened successfully." );
//        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("next"));
        }catch (Exception e){

        }
    }
    public void fnselectreviewerSubmitter(ConfigTestRunner configTestRunner){
        if(getWebElement("selectAddSubmiReviewer").isDisplayed()){
            configTestRunner.getChildTest().log(Status.PASS, "Select Additional Submitters and Reviewers Window is opened successfully.");
        }else
            configTestRunner.getChildTest().log(Status.FAIL, "Select Additional Submitters and Reviewers Window is not opened successfully.");
        fnAddReviewer(configTestRunner);
        fnAddSubmitter(configTestRunner);
        //click on Next button
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("nextButton"));
    }
    public void fnDescFileSelect(ConfigTestRunner configTestRunner){
        try{
            if(fnWaitForVisibility(getWebElement("descriptionPopUpMessage"),10)){
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("descriptionPopUpMessageOk"),Constants.waitForEleLoad);
//                getWebElement("description").sendKeys(configTestRunner.getTestData().get("description"));
//                configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"),Constants.waitForEleLoad);
            }
        }catch (Exception ex){

        }
        fnWaitForVisibility(getWebElement("description"),30);
        sleep(1000);
        getWebElement("description").sendKeys(configTestRunner.getTestData().get("description"));
        WebElement docType = getWebElement("documentType");
        configTestRunner.getCommonMethods().selectComboboxSendKeys(docType, configTestRunner, configTestRunner.getTestData().get("documentType"));
        configTestRunner.getChildTest().log(Status.PASS,"Document Type selected is: "+configTestRunner.getTestData().get("documentType"));


        WebElement docSubType = getWebElement("documentSubType");
        configTestRunner.getCommonMethods().selectComboboxSendKeys(docSubType,configTestRunner,configTestRunner.getTestData().get("documentSubType"));
        configTestRunner.getChildTest().log(Status.PASS,"Document Sub Type selected is: "+configTestRunner.getTestData().get("documentSubType"));

        //Select public checkbox
        //configTestRunner.getCommonMethods().waitAndClick(getWebElement("securityCheckBoxPublic"),Constants.waitForEleLoad);
        //select documet
        WebElement browser =driver.findElement(By.cssSelector("input[type='file']"));
        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor)driver).executeScript(js,browser);
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
        sleep(300);
        browser.sendKeys(filePath);


//        WebElement element = getWebElement("chooseFileUpload");
////        configTestRunner.getCommonMethods().setFocusWithoutClick(element,"C:\\Users\\DhageJ\\IdeaProjects\\StateOfCT.stageExternal.com\\UploadDocument\\Image.jpg");
//        configTestRunner.getCommonMethods().setFocusClick(element);
//        String filePath = System.getProperty("user.dir") + "\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
//        sleep(300);
//        fnFileUploadFunctionality(configTestRunner,filePath);

        sleep(300);
        //click on attachment document
        if(getWebElement("attachDocumentBtton").isEnabled()) {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "attachDocumentBtton"), Constants.AJAX_TIMEOUT);
            configTestRunner.getCommonMethods().setFocusClick(getWebElement( "attachDocumentBtton"));
        }
        try {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("documentTypeHeader"), Constants.AJAX_TIMEOUT);
            if (getWebElement("documentTypeHeader").isDisplayed())
                configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
            else
                configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
        }catch (Exception e) {

        }
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"),Constants.waitForEleLoad);
//        try{
//            if(fnWaitForVisibility(getWebElement("descriptionPopUpMessage"),30)){
//                configTestRunner.getCommonMethods().waitAndClick(getWebElement("descriptionPopUpMessageOk"),Constants.waitForEleLoad);
//                getWebElement("description").sendKeys(configTestRunner.getTestData().get("description"));
//                configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"),Constants.waitForEleLoad);
//            }
//        }catch (Exception ex){
//
//        }
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
    public void fnSearchExistingMatter(ConfigTestRunner configTestRunner, String MatterNO){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Search Existing Matter Verification"));
        fnWaitForVisibility(getWebElement("searchMatterHeader"),Constants.waitForEleLoad);
        if(getWebElement("searchMatterHeader").isDisplayed()){
            if(!(MatterNO.isEmpty())) {
                getWebElement("searchMatterNo").sendKeys(MatterNO);
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("searchGoButton"), Constants.waitForEleLoad);
//                fnWaitForVisibility(getWebElement("searchHeaderAfter"), Constants.waitForEleLoad);
                if (configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,MatterNO).isDisplayed()) {
                    configTestRunner.getChildTest().log(Status.PASS, MatterNO + " matter is searched successfully.");
                    Assert.assertTrue(getspanWithText(configTestRunner, configTestRunner.getTestData().get("industryType")).isDisplayed(), "Industry/Utility type is displayed correctly after created matter searched.");
                    Assert.assertTrue(getspanWithText(configTestRunner, configTestRunner.getTestData().get("matterType")).isDisplayed(), "Matter Type is displayed correctly after created matter searched.");
                    Assert.assertTrue(getspanWithText(configTestRunner, configTestRunner.getTestData().get("MatterSubType")).isDisplayed(), "Matter Sub Type is displayed correctly after created matter searched.");
//                    Assert.assertTrue(getSpanContainsWithText(configTestRunner, configTestRunner.getTestData().get("subscribeAggrement")).isDisplayed(), "Partaning To is displayed correctly after created matter searched.");
//                    Assert.assertTrue(getSpanContainsWithText(configTestRunner, configTestRunner.getTestData().get("subscribeAggrement")).isDisplayed(), "Matter Title is displayed correctly after created matter searched.");
//                    Assert.assertTrue(getSpanContainsWithTextfiledBy(configTestRunner, configTestRunner.getTestData().get("name")).isDisplayed(), " Filied by is displayed correctly after created matter searched.");

                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Existing matter is not searched successfully.");
            }else
                configTestRunner.getChildTest().log(Status.FAIL,  "Matter number is not selected for search.");
        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Search existing matter screen is not opened successfuly");

    }
    public void fnOpenOnBehalfScreen(ConfigTestRunner configTestRunner){
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("next"),Constants.waitForEleLoad);
        fnWaitForVisibility(getWebElement("associatedFiling"),Constants.waitForEleLoad);
        if(getWebElement("associatedFiling").isDisplayed())
            configTestRunner.getChildTest().log(Status.PASS,"On Behalf Of Screen is opened successfully");
        else
            configTestRunner.getChildTest().log(Status.FAIL,"On Behalf Of Screen is not opened successfully");
        //click on next button
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("next"),Constants.waitForEleLoad);
    }
    public void fnMatterConfirmation(ConfigTestRunner configTestRunner){
        //click on submit button
        try{
            if(getWebElement("submitButton").isDisplayed())
                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("submitButton"));

        }catch (Exception e){
            if(getWebElement("submitButtonNew").isDisplayed())
                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("submitButtonNew"));
        }
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Matter Confirmation Verification"));
        try {
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
                        configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                    } catch (Exception e) {

                    }
                    logger.info("Matter is created with matter no :" + configTestRunner.MatterNumber);
                } else {
                    String[] matterno = getWebElement("confirmationMessage").getText().split(" ");
                    configTestRunner.FAMatterNumber = matterno[matterno.length - 1];
                    logger.info("FA Matter is created with matter no :" + configTestRunner.FAMatterNumber);
                    configTestRunner.data.setCellData(configTestRunner.FAMatterNumber, configTestRunner.data.rowValue(configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO", "TestCase")), configTestRunner.data.ColumnValue("FA_Matter", "TestCase"));
                    configTestRunner.MatterNumber = matterno[matterno.length - 6];
                    logger.info("Matter is created with matter no :" + configTestRunner.MatterNumber);
                    configTestRunner.data.setCellData(configTestRunner.MatterNumber, configTestRunner.data.rowValue(configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO", "TestCase")), configTestRunner.data.ColumnValue("Matter_No", "TestCase"));
                    configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully & the matter number is :" + getWebElement("confirmationMessage").getText());
                    try {
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        configTestRunner.getChildTest().log(Status.PASS, "Matter is created successfully " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

                    } catch (Exception e) {

                    }
                }
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Matter is not created successfully & the matter number is not created:");
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL, "Matter is not created successfully & the matter number is not created :");

        }
        Assert.assertTrue(getWebElement("confirmationMessage").isDisplayed());
        if(configTestRunner.getTestCase().get("ApplicationType").contains("PURA"))
            configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,"PURA E-Filing"),Constants.waitForEleLoad);
        else
            configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getlinkWithText(configTestRunner,"BETP"),Constants.waitForEleLoad);
    }

    public void fnAddSubmitter(ConfigTestRunner configTestRunner) {
        if(!(configTestRunner.getTestData().get("submitterUserID").equals(null))) {
            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("addSubmitter"), Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("addSubmitter"), Constants.waitForEleLoad);
                configTestRunner.getCommonMethods().setFocus(getWebElement("addSubmitter_userID"), configTestRunner.getTestData().get("submitterUserID"));
                configTestRunner.getChildTest().log(Status.PASS, "Submitter added is :" + configTestRunner.getTestData().get("submitterUserID"));
                //click on done button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("addSubmitter_doneButton"), 60);
            }
        }
    }
    public void fnAddReviewer(ConfigTestRunner configTestRunner){
        if(!(configTestRunner.getTestData().get("ReviewerUserID").equals(null))) {
            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("addReviewer"), Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("addReviewer"), Constants.waitForEleLoad);
                configTestRunner.getCommonMethods().setFocus(getWebElement("addSubmitter_userID"), configTestRunner.getTestData().get("ReviewerUserID"));
                configTestRunner.getChildTest().log(Status.PASS, "Reviewer added is :" + configTestRunner.getTestData().get("ReviewerUserID"));
                //click on done button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("addSubmitter_doneButton"), 60);
            }
        }
    }
    public void fnOBOInformation(ConfigTestRunner configTestRunner){
        configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("summaryResult"),Constants.AJAX_TIMEOUT);
        if(getWebElement("summaryResult").isDisplayed()) {
            configTestRunner.getChildTest().log(Status.PASS, "Companies/Entities/Individuals Associated to this Filing summary window is opened successfully.");
            Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'"+ configTestRunner.getTestData().get("subscribeAggrement")+"')]")).isDisplayed(), "OBO window is opened successfully & contains the selected company name");
        }else
            configTestRunner.getChildTest().log(Status.FAIL,"Companies/Entities/Individuals Associated to this Filing summary window is not opened successfully." );
        configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("next"));

    }

}
