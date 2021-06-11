package com.stateofconnecticut.selenium.Application.Modules;

import com.aventstack.extentreports.Status;
import com.stateofconnecticut.selenium.config.Configuration;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import com.stateofconnecticut.selenium.utilities.Constants;
import com.sun.org.apache.bcel.internal.Const;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InternalMatterCreation extends BaseAction {
    protected static final Log logger = LogFactory.getLog(LoginAction.class);
    public InternalMatterCreation(WebDriver driver, Configuration conf) {
        super(driver, conf);
    }

    @Override
    public String getFormName() {
        return "InterNewMatter";
    }

    public void internalMatterVerification(ConfigTestRunner configTestRunner) {
        if (searchMatter(configTestRunner, configTestRunner.MatterNumber)) {
            selectRoutingHistory(configTestRunner);
            if (selectRoutingHistory(configTestRunner)) {

            }

        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Matter is not searched successfully");
    }

    public boolean searchMatter(ConfigTestRunner configTestRunner, String matterNo) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Search Created Matter Verification In IBM Case Manager"));
        boolean findMatter = false;
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getspanWithText(configTestRunner, "Search"), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getspanWithText(configTestRunner, "Search"), Constants.waitForEleLoad);
            sleep(2000);
            fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_Iframe"), Constants.waitForEleLoad);
            driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_Iframe"));
            if (fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_MatterNumber"), Constants.waitForEleLoad)) {
                getWebElement("InternalMatterFiling", "search_MatterNumber").sendKeys(matterNo);
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("InternalMatterFiling", "search_goButton"), Constants.waitForEleLoad);
                sleep(4000);
                driver.switchTo().defaultContent();
                fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
                driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
                sleep(10000);
                if (fnWaitForVisibility(getWebElement("PURA", "search_PuraDocketMatter"), Constants.waitForEleLoad)) {
                    configTestRunner.getChildTest().log(Status.PASS, "Matter is searched successfully in internal application");
                    driver.switchTo().defaultContent();
                    findMatter = true;
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Created Matter is not search successfully in internal application");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "In IBM Case manager Search screen is not opened successfuly.");
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "In IBM Case manager Search icon is not opened successfuly.");
        return findMatter;
    }

    public boolean selectRoutingHistory(ConfigTestRunner configTestRunner) {
        boolean fnstatus = false;
        fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("PURA", "routingHistory"), Constants.waitForEleLoad);
        sleep(6000);
        fnWaitForVisibility(getWebElement("PURA", "routingIframe"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("PURA", "routingIframe"));
        if (fnWaitForVisibility(getWebElement("PURA", "viewConsoRoutingHistory"), Constants.waitForEleLoad)) {
            fnstatus = true;
            configTestRunner.getCommonMethods().sendKeysPageDown();
            configTestRunner.getChildTest().log(Status.PASS, "Routing History tab is selected successfully.");
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Routing History tab is not selected successfully.");
        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
        return fnstatus;
    }
    public boolean selectPanelAfterSearch(ConfigTestRunner configTestRunner,String tab,String locator) {
        boolean fnstatus = false;
        fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
        fnWaitForVisibility(configTestRunner.getCommonMethods().getPrecontainsWithText(configTestRunner, tab),Constants.waitForEleLoad);
        configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getPrecontainsWithText(configTestRunner, tab), Constants.waitForEleLoad);
        sleep(6000);
        fnWaitForVisibility(getWebElement("PURA", "routingIframe"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("PURA", "routingIframe"));
        if (fnWaitForVisibility(getWebElement( locator), Constants.waitForEleLoad)) {
            fnstatus = true;
            configTestRunner.getCommonMethods().sendKeysPageDown();
            configTestRunner.getChildTest().log(Status.PASS, tab+" tab is selected successfully.");
        } else
            configTestRunner.getChildTest().log(Status.FAIL, tab+" tab is not selected successfully.");
        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
        return fnstatus;
    }

    public void fnAddTimeSchedule(ConfigTestRunner configTestRunner){
        fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
        fnWaitForVisibility(getWebElement("btnSearchIframe"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement( "btnSearchIframe"));
        fnWaitForVisibility(getWebElement( "manageTimeschedulebtn"), Constants.waitForEleLoad);
        configTestRunner.getCommonMethods().waitAndClick(getWebElement( "manageTimeschedulebtn"),Constants.waitForEleLoad);
        driver.switchTo().defaultContent();
        String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
        WebDriver popup = null;
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        for (int i=1 ;i<newTab.size();i++){
            String window =driver.getWindowHandle();
            driver.switchTo().window(newTab.get(i));
        }
        newTab=null;
        driver.getTitle();
        fnWaitForVisibility(getWebElement("frameAddNewEvent"),Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("frameAddNewEvent"));
        List<String> eventField = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("internal_AddEvent").split(",")));
        for(int i=0;i<eventField.size();i++){
        if(fnWaitForVisibility(getWebElement("btnAddNewEvent"),Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnAddNewEvent"), Constants.waitForEleLoad);
            newTab = new ArrayList<String>(driver.getWindowHandles());
            for (int j = 1; j <= newTab.size(); j++) {
                driver.switchTo().window(newTab.get(j));
                if (fnWaitForVisibility(getWebElement("addEvent"), Constants.waitForEleLoad)) {
                    configTestRunner.getCommonMethods().setdataSendKeysEnter(getWebElement("addEvent"), eventField.get(i));
                    //add date
                    String date = configTestRunner.getCommonMethods().getTodaysDate(configTestRunner, "MM/dd/yyyy");
                    configTestRunner.getCommonMethods().setFocus(getWebElement("addDate"), date);
                    //Add Location
                    configTestRunner.getCommonMethods().setdataSendKeysEnter(getWebElement("addlocation"), configTestRunner.getTestData().get("internal_addLocation"));
                    configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getPrecontainsWithText(configTestRunner, "Add Event"), Constants.waitForEleLoad);
                    sleep(6000);

                }
                driver.switchTo().defaultContent();
                driver.close();
                driver.switchTo().window(parentWindowHandle);
                driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
                fnWaitForVisibility(getWebElement("btnSearchIframe"), Constants.waitForEleLoad);
                driver.switchTo().frame(getWebElement("btnSearchIframe"));

                configTestRunner.getCommonMethods().waitAndClick(getWebElement("manageTimeschedulebtn"), Constants.waitForEleLoad);
                newTab = new ArrayList<String>(driver.getWindowHandles());
                for (int k = 1; k < newTab.size(); k++) {
                    String window = driver.getWindowHandle();
                    driver.switchTo().window(newTab.get(k));
                }
                driver.switchTo().frame(getWebElement("frameAddNewEvent"));
                newTab = null;
                try {
                    if (fnWaitForVisibility(getTDWithText(configTestRunner, eventField.get(i)), Constants.waitForEleLoad))
                        configTestRunner.getChildTest().log(Status.PASS, eventField.get(i) + " Event is added successfully.");
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, eventField.get(i) + " Event is not added successfully.");
                }catch (Exception e){
                    configTestRunner.getChildTest().log(Status.FAIL, eventField.get(i) + " Event is not added successfully.");
                }
                //Save the Event
                configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().saveImage(configTestRunner, eventField.get(i)), Constants.waitForEleLoad);
                break;

            }
        }


        }
        driver.switchTo().defaultContent();
        driver.close();
        driver.switchTo().window(parentWindowHandle);

    }

    public void fnNewMatterDistStatus(ConfigTestRunner configTestRunner) {
        fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("PURA", "routingHistory"), Constants.waitForEleLoad);
        sleep(6000);
        fnWaitForVisibility(getWebElement("PURA", "routingIframe"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("PURA", "routingIframe"));
        fnRoutingStatus(configTestRunner);
        driver.switchTo().defaultContent();
        driver.switchTo().defaultContent();
    }

    public void fnRoutingStatus(ConfigTestRunner configTestRunner) {
        configTestRunner.getCommonMethods().sendKeysPageDown();
        List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
        int rowNo = rows.size();
        for (int t = 2; t <= 5; t++) {
            try {
                List<WebElement> column = driver.findElements(By.xpath("//table//tr[" + t + "]//td"));
                String Action = driver.findElement(By.xpath("//table//tr[" + t + "]//td[1]//a")).getText();
                if (Action.equals("NMF Submission")) {
                    boolean matterNMFstatus = false;
                    boolean matterNMFComplitionstatus = false;
                    String Data = null;
                    for (int n = 1; n < column.size(); n++) {
                        Data = driver.findElement(By.xpath("//table//tr[2]//td[" + n + "]")).getText();
                        if (Data.equals("Received") || Data.equals("Open")) {
                            matterNMFstatus = true;
                        } else if (Data.equals("Y")) {
                            matterNMFComplitionstatus = true;
                        }
                    }
                    if (matterNMFstatus)
                        configTestRunner.getChildTest().log(Status.PASS, "NMF Submission is received and the Matter/Docket Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "NMF Submission is not received and the Matter/Docket Status is " + Data);
                    if (matterNMFComplitionstatus)
                        configTestRunner.getChildTest().log(Status.PASS, "NMF Submission is received and the Action Completion Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "NMF Submission is received and the Action Completion Status is not " + Data);
                } else if (Action.equals("Matter Review (Approved/Rejected)")) {
                    boolean matterReviewStatus = false;
                    boolean matterReviewCompStatus = false;
                    String Data = null;
                    for (int n = 1; n < column.size(); n++) {
                        Data = driver.findElement(By.xpath("//table//tr[" + t + "]//td[" + n + "]")).getText();
                        if (Data.equals("Received") || Data.equals("Open")) {
                            matterReviewStatus = true;
                        } else if (Data.equals("Y")) {
                            matterReviewCompStatus = true;
                        }
                    }
                    if (matterReviewStatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Matter is reviewed and the Matter/Docket Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "Matter is not reviewed and the Matter/Docket Status is " + Data);
                    if (matterReviewCompStatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Matter Reviewed is received and the Action Completion Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.PASS, "Matter Reviewed is noy received and the Action Completion Status is not" + Data);
                } else if (Action.equals("Staff Assignment")) {
                    boolean staffAssigmentStatus = false;
                    boolean staffAssigmentCompStatus = false;
                    String Data = null;
                    for (int n = 1; n < column.size(); n++) {
                        Data = driver.findElement(By.xpath("//table//tr[" + t + "]//td[" + n + "]")).getText();
                        if (Data.equals("Received") || Data.equals("Open")) {
                            staffAssigmentStatus = true;

                        } else if (Data.equals("Y")) {
                            staffAssigmentCompStatus = true;
                        }
                    }
                    if (staffAssigmentStatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Staff Assignment is completed and the status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "Staff Assignment is not completed and the status is " + Data);
                    if (staffAssigmentCompStatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Staff Assignment is received and the Action Completion Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "Staff Assignment is not received and the Action Completion Status is " + Data);
                } else if (Action.equals("Distribution List")) {
                    boolean distributionstatus = false;
                    boolean distributionCompstatus = false;
                    String Data = null;
                    for (int n = 1; n <= column.size(); n++) {
                        Data = driver.findElement(By.xpath("//table//tr[" + t + "]//td[" + n + "]")).getText();
                        if (Data.equals("Received") || Data.equals("Open")) {
                            distributionstatus = true;
                            // driver.findElement(By.xpath("//table//tr[" + t + "]//td[" + n + "]")).click();
                        } else if (Data.equals("Y")) {
                            distributionCompstatus = true;

                        }
                    }
                    if (distributionstatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Distribution List is published successfully and the status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "Distribution List is not published successfully and the status is " + Data);
                    if (distributionCompstatus)
                        configTestRunner.getChildTest().log(Status.PASS, "Distribution List is published successfully  and the Action Completion Status is " + Data);
                    else
                        configTestRunner.getChildTest().log(Status.FAIL, "Distribution List is not published successfully  and the Action Completion Status is " + Data);
                }
            } catch (Exception e) {

            }
        }
        try {
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            configTestRunner.getChildTest().log(Status.PASS, "Routing history of the matter" + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(name )));

        } catch (Exception e) {
        }
    }

    public void viewRouthingHistoryClick(ConfigTestRunner configTestRunner) {
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("PURA", "viewConsoRoutingHistory"), 120);
        if (fnWaitForVisibility(getWebElement("PURA", "viewConsoRoutingHistory"), 120)) {


        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Routing History tab is not selected successfully.");
    }

    public void fnRoleSelection(ConfigTestRunner configTestRunner, String Role) {

        if (getWebElement("puraRoleSelection").isDisplayed()) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("puraRoleSelection"), Constants.waitForEleLoad);
            configTestRunner.getCommonMethods().waitAndClick(getspanWithText(configTestRunner, Role), Constants.waitForEleLoad);

        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Role Selection Picker is not available");
    }

    public void fnSelectTab(ConfigTestRunner configTestRunner, String tabname) {
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getspanWithText(configTestRunner, tabname), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getspanWithText(configTestRunner, tabname), 30);
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Work Tab is not selected successfully.");
    }

    public void fnNewMatterReviewQueue(ConfigTestRunner configTestRunner) {
        WebElement reviewTab = configTestRunner.getCommonMethods().getlinkContainsWithText(configTestRunner, "New Matter Review Queue");
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(reviewTab, Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(reviewTab, Constants.waitForEleLoad);
            if (getWebElement("newMatterReviewQueue_MatterDocketNumbare").isDisplayed()) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("newMatterReviewQueue_MatterDocketNumbare"), Constants.waitForEleLoad);
                if (getTDWithText(configTestRunner, configTestRunner.MatterNumber).isDisplayed()) {
                    configTestRunner.getChildTest().log(Status.PASS, configTestRunner.MatterNumber + " This matter is selected successfully from new matter review queue.");
                    configTestRunner.getCommonMethods().setFocusdblClick(getTDWithText(configTestRunner, configTestRunner.MatterNumber));
                    driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
                    if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("elevMatterToDocketYes"), Constants.waitForEleLoad)) {
                        configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully.");
                    } else
                        configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully.");
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, configTestRunner.MatterNumber + " :This matter number is not displayed into the new matter review table");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "New Matter Review Queue table is not displayed.");
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "New Matter Review Queue is not displayed.");
        driver.switchTo().defaultContent();
    }

    public void fnAcceptReviewMatter(ConfigTestRunner configTestRunner, String elevMatterToDocketYes, String contested, String assignDocketNumber_nextAvailable, String accept) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Contested type selection form new matter review panel"));
        sleep(2000);
        WebElement browser = driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"));
        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor) driver).executeScript(js, browser);
        fnWaitForVisibility(browser, Constants.waitForEleLoad);
//        fnWaitForVisibility(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("elevMatterToDocketYes"), Constants.waitForEleLoad)) {
            if (elevMatterToDocketYes.equals("Yes")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("elevMatterToDocketYes"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Elevated to dockey selected is Yes");
            } else if (elevMatterToDocketYes.equals("No")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("elevMatterToDocketNo"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Elevated to dockey selected is No");
            }
            if (contested.equals("Yes")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("contested"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Contested case is selected.");
            } else if (contested.equals("No")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("uncontested"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "UnContested case is selected.");
            }
            if (assignDocketNumber_nextAvailable.equals("Yes")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("assignDocketNumber_nextAvailable"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Assign docket number to next available");
            } else if (assignDocketNumber_nextAvailable.equals("No")) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("assignDocketNumber_reserved"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Assign docket number to reserved");
            }
            getWebElement("filingReviewComment").sendKeys(configTestRunner.getTestData().get("reviewerComment"));
        }
        if (accept.equals("Yes")) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("filingReviewAccept"), Constants.waitForEleLoad);
            sleep(3000);
            try {
//                driver.switchTo().alert().accept();
                String[] text = driver.switchTo().alert().getText().split(" ");
                String docketNumber = text[text.length - 1];
                configTestRunner.data.setCellData(docketNumber, configTestRunner.data.rowValue(configTestRunner.getTestCase().get("SR_NO"), configTestRunner.data.ColumnValue("SR_NO", "TestCase")), configTestRunner.data.ColumnValue("Docket_Number", "TestCase"));
                driver.switchTo().alert().accept();
            } catch (Exception e) {

            }
            configTestRunner.getChildTest().log(Status.PASS, "Matter is accepted successfully.");
        } else if (accept.equals("No")) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("filingReviewReject"), Constants.waitForEleLoad);
            configTestRunner.getChildTest().log(Status.PASS, "Matter is rejected successfully.");
        }
        try {
            driver.switchTo().defaultContent();
        } catch (Exception w) {

        }
    }

    public boolean fnNewMatterStaffAssignment(ConfigTestRunner configTestRunner, String tabName, String prevColName, String columName, String verificationele) {
        boolean findFlag = false;
        WebElement reviewTab = configTestRunner.getCommonMethods().getlinkContainsWithText(configTestRunner, tabName);
        try {
            if (configTestRunner.getCommonMethods().fnWaitForVisibility(reviewTab, Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(reviewTab, Constants.waitForEleLoad);
                sleep(500);
                configTestRunner.getCommonMethods().waitAndClick(getSpanContainsWithText(configTestRunner, tabName), Constants.waitForEleLoad);
                sleep(500);
                configTestRunner.getCommonMethods().fnWaitForVisibility(configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName), Constants.waitForEleLoad);
                if (configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName).isDisplayed()) {
                    configTestRunner.getCommonMethods().sendKeysPageDown();
                    configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName), Constants.waitForEleLoad);
                    for (int i = 1; i <= 20; i++) {
                        try {
                            if (!(fnWaitForVisibility(getTDWithText(configTestRunner, configTestRunner.MatterNumber),30))) ;
                        } catch (Exception ex) {
                            configTestRunner.getCommonMethods().sendKeysPageDown();
                            driver.findElement(By.xpath("(//div[@class='gridxVScroller'])[2]")).sendKeys(Keys.END);
                            driver.findElement(By.xpath("(//div[@class='gridxVScroller'])[2]")).sendKeys(Keys.END);
                        }
                    }
                    if (getTDWithText(configTestRunner, configTestRunner.MatterNumber).isDisplayed()) {
                        configTestRunner.getChildTest().log(Status.PASS, configTestRunner.MatterNumber + " This matter is selected successfully from new matter review queue.");
                        configTestRunner.getCommonMethods().setFocusdblClick(getTDWithText(configTestRunner, configTestRunner.MatterNumber));
                        sleep(2000);
                        fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
                        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
                        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement(verificationele), Constants.waitForEleLoad)) {
                            configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully in: " + tabName);
                            findFlag = true;
                        } else
                            configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully in: " + tabName);
                    } else
                        configTestRunner.getChildTest().log(Status.FAIL, configTestRunner.MatterNumber + " :This matter number is not displayed into the new matter review table");
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, tabName + " table is not displayed.");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, tabName + " is not displayed.");
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {

        }
        return findFlag;
    }

    public boolean fnNewMatterStaffAssignmentDist(ConfigTestRunner configTestRunner, String tabName, String prevColName, String columName, String verificationele) {
        boolean findFlag = false;
        WebElement reviewTab = configTestRunner.getCommonMethods().getlinkContainsWithText(configTestRunner, tabName);
        try {
            if (configTestRunner.getCommonMethods().fnWaitForVisibility(reviewTab, Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(reviewTab, Constants.waitForEleLoad);
                sleep(500);
                configTestRunner.getCommonMethods().waitAndClick(getSpanContainsWithText(configTestRunner, tabName), Constants.waitForEleLoad);
                sleep(500);
                configTestRunner.getCommonMethods().fnWaitForVisibility(configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName), Constants.waitForEleLoad);
                if (configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName).isDisplayed()) {
//                    configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner,prevColName, columName), Constants.waitForEleLoad);
                    try {
                        if (getTDWithText(configTestRunner, configTestRunner.MatterNumber).isDisplayed()) {
                            configTestRunner.getChildTest().log(Status.PASS, configTestRunner.MatterNumber + " This matter is selected successfully from new matter review queue.");
                            configTestRunner.getCommonMethods().setFocusdblClick(getTDWithText(configTestRunner, configTestRunner.MatterNumber));
                            fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
                            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
                            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement(verificationele), Constants.waitForEleLoad)) {
                                configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully in: " + tabName);
                                findFlag = true;
                            } else
                                configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully in: " + tabName);
                        } else
                            configTestRunner.getChildTest().log(Status.FAIL, configTestRunner.MatterNumber + " :This matter number is not displayed into the new matter review table");

                    } catch (Exception e) {
                        configTestRunner.getCommonMethods().getdivContainsWithTextTabSel(configTestRunner, prevColName, columName).click();
                        if (getTDWithText(configTestRunner, configTestRunner.MatterNumber).isDisplayed()) {
                            configTestRunner.getChildTest().log(Status.PASS, configTestRunner.MatterNumber + " This matter is selected successfully from new matter review queue.");
                            configTestRunner.getCommonMethods().setFocusdblClick(getTDWithText(configTestRunner, configTestRunner.MatterNumber));
                            fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
                            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
                            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement(verificationele), Constants.waitForEleLoad)) {
                                configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully in: " + tabName);
                                findFlag = true;
                            } else
                                configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully in: " + tabName);
                        } else
                            configTestRunner.getChildTest().log(Status.FAIL, configTestRunner.MatterNumber + " :This matter number is not displayed into the new matter review table");

                    }
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, tabName + " table is not displayed.");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, tabName + " is not displayed.");
            driver.switchTo().defaultContent();
        } catch (Exception e) {

        }
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {

        }
        return findFlag;
    }

    public void fnSelectStaffandCommisioner(ConfigTestRunner configTestRunner) {
        int count = 1;
        int a = 0;
        int b = 0;
        List<String> Rolefield = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("staff_Role").split(",")));
        List<String> stafffields = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("StaffUserID").split(",")));
        try {
            for (int m = a; m < Rolefield.size(); m++) {
                for (int n = b; n < stafffields.size(); n++) {
                    driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
                    WebElement staffRadioButton = getWebElement("staffAssignment_staff");
                    WebElement staffInput = getWebElement("staffAssignment_staff_input");
                    WebElement staffRole = getWebElement("staffAssignment_staff_Role");

                    if (configTestRunner.getCommonMethods().fnWaitForVisibility(staffRadioButton, Constants.waitForEleLoad)) {
                        sleep(2000);
                        //select staff
                        configTestRunner.getCommonMethods().waitAndClick(staffRadioButton, Constants.waitForEleLoad);
                        configTestRunner.getChildTest().log(Status.PASS, "Staff radio butto is selected successfully.");
                        sleep(1000);
                        //select Role

                        configTestRunner.getCommonMethods().setdataSendKeysEnter(staffRole, Rolefield.get(m));
                        configTestRunner.getChildTest().log(Status.PASS, Rolefield.get(m) + " role is selected successfully");
                        if (!(stafffields.get(n).equals(null))) {
                            sleep(1000);
                            //Select Person user id
                            configTestRunner.getCommonMethods().setdataSendKeysEnter(staffInput, stafffields.get(n));
                            configTestRunner.getChildTest().log(Status.PASS, stafffields.get(n) + " user id is selected successfully");
                        }
                        sleep(800);
                        //click on assign button
                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("staffAssignment_AssignButton"), Constants.waitForEleLoad);
                        sleep(2000);
                        configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")), Constants.waitForEleLoad);
                        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")));
                        if (count % 2 == 0) {
                            configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//tr[@class='even']")), Constants.waitForEleLoad);
                            configTestRunner.getChildTest().log(Status.PASS, "Staff is added successfully.");
                            count++;
                        } else {
                            configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//tr[@class='odd']")), Constants.waitForEleLoad);
                            configTestRunner.getChildTest().log(Status.PASS, "Staff is added successfully.");
                            count++;
                        }
                        staffRadioButton = null;
                        staffInput = null;
                        staffRole = null;
                        driver.switchTo().defaultContent();
                    } else
                        configTestRunner.getChildTest().log(Status.FAIL, "Staff Assignment window is not opened successfully.");
                    b = b + 1;
                    break;
                }
                a = a + 1;
            }
            driver.switchTo().defaultContent();
        } catch (Exception e) {

        }

        fnSelectComissioner(configTestRunner);
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")));
        //send to sendToTechChief
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("sendToTechChief"), Constants.waitForEleLoad);
        if (driver.switchTo().alert().getText().contains("Workitem routed successfully")) {
            configTestRunner.getChildTest().log(Status.PASS, "Work Item sent successfully to the technical chief.");
            driver.switchTo().alert().accept();
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Work Item is not sent successfully to the technical chief.");

        driver.switchTo().defaultContent();
    }

    public void fnSelectComissioner(ConfigTestRunner configTestRunner) {
        int cnt = 1;
        int t = 0;
        int k = 0;
        sleep(1000);
        driver.switchTo().defaultContent();
        int count = Integer.parseInt(configTestRunner.getTestData().get("commissionerLeadCount"));
//        int nonLeadcount =Integer.parseInt(configTestRunner.getTestData().get("CommissionernonLead_Count"));
        List<String> fields = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("commisioner_Role").split(",")));
        List<String> staffUserId_fields = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("Commisioner_UserID").split(",")));
        for (int j = k; j < fields.size(); j++) {

            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
            WebElement commisionerRadioButton = getWebElement("staffAssignment_commitionerPanel");
            WebElement commisionerInput = getWebElement("staffAssignment_commitionerPanel_input");
            WebElement staffRole = getWebElement("staffAssignment_staff_Role");
            WebElement nonLead = getWebElement("staffAssignment_commitionerPanel_nonLead");
            WebElement leadRole = getWebElement("staffAssignment_commitionerPanel_Lead");

            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("staffAssignment_staff"), Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(commisionerRadioButton, Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.PASS, "Commisioner radio button is selected successfully.");
                sleep(1000);
                //Click on commisioner radio button
                if (!(fields.get(j).equals(null))) {
                    //select Role
                    configTestRunner.getCommonMethods().setdataSendKeysEnter(staffRole, fields.get(j));
                    configTestRunner.getChildTest().log(Status.PASS, fields.get(j) + " role is selected successfully");
//                    staffRole.sendKeys(Keys.TAB);
//                    staffRole.sendKeys(Keys.TAB);
//                    staffRole.sendKeys(Keys.DOWN);
                }
                for (int i = t; i < staffUserId_fields.size(); i++) {
                    if (!(staffUserId_fields.get(i).equals(null))) {
                        //Select Person user id
                        sleep(1400);
                        //configTestRunner.getCommonMethods().setdataSendKeysEnter(commisionerInput, staffUserId);
//                        driver.findElement(By.xpath("(//input[contains(@value,'▼')])[5]")).click();
                        WebElement browser = driver.findElement(By.xpath("//input[contains(@id,'ManageAssignedStaff.MatterAssignmentPane-1_19')]"));
                        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
                        configTestRunner.getCommonMethods().setdataSendKeysEnter(browser, staffUserId_fields.get(i));
//                        configTestRunner.getCommonMethods().waitAndClick(configTestRunner.getCommonMethods().getdivContainsWithText(configTestRunner,staffUserId_fields.get(i)),30);
                        configTestRunner.getChildTest().log(Status.PASS, staffUserId_fields.get(i) + " user id is selected successfully");
                        t = t + 1;
                    }
                    break;
                }
                try {
                    if (count >= 1) {
                        configTestRunner.getCommonMethods().waitAndClick(leadRole, 30);
                        configTestRunner.getChildTest().log(Status.PASS, " Admin role is selected successfully.");
                        count = count - 1;
                    }
//                    else {
//                        if (nonLeadcount >= 1) {
//                            configTestRunner.getCommonMethods().waitAndClick(nonLead, 30);
//                            configTestRunner.getChildTest().log(Status.PASS, "Non Admin role is selected successfully.");
//                            nonLeadcount=nonLeadcount-1;
//                        }
//                    }
                } catch (Exception e) {

                }
                try {
                    //click on assign button
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("staffAssignment_AssignButton"), Constants.waitForEleLoad);
                    sleep(2000);
                    configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")), Constants.waitForEleLoad);
                    driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")));
                    if (cnt % 2 == 0) {
                        sleep(900);
                        configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//tr[@class='even']")), Constants.waitForEleLoad);
                        configTestRunner.getChildTest().log(Status.PASS, "Commisioner is added successfully.");
                        cnt++;
                    } else {
                        sleep(900);
                        configTestRunner.getCommonMethods().fnWaitForVisibility(driver.findElement(By.xpath("//tr[@class='odd']")), Constants.waitForEleLoad);
                        configTestRunner.getChildTest().log(Status.PASS, "Commisioner is added successfully.");
                        cnt++;
                    }
                }catch (Exception e){

                }
                leadRole = null;
                commisionerInput = null;
                staffRole = null;
                nonLead = null;
                commisionerRadioButton = null;
                driver.switchTo().defaultContent();
                k = k + 1;
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Staff Assignment window is not opened successfully.");
        }
    }

    public void fnApproveStaffAssignment(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Staff Assignment Approval Verification"));
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("staffAssignment_staff"), Constants.waitForEleLoad)) {
            configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully for staff approval.");
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")));
            configTestRunner.getCommonMethods().sendKeysPageDown();
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("StaffApprove"), Constants.waitForEleLoad);
            if (driver.switchTo().alert().getText().contains("Workitem approved successfully")) {
                driver.switchTo().alert().accept();
                driver.switchTo().defaultContent();
                configTestRunner.getChildTest().log(Status.PASS, "Assigned staff is approve successfully.");
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Assigned staff is not approve successfully.");
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully for staff approval.");
    }

    public void fnComplitionStaffAssignment(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Staff Assignment complition Verification"));
        configTestRunner.getCommonMethods().sendKeysPageDown();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("staffAssignment_staff"), Constants.waitForEleLoad)) {
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'ManageAssignedStaff.ManageStaffAssigned')]")));
            configTestRunner.getChildTest().log(Status.PASS, "Selected matter is opened successfully for staff complition.");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("StaffAssignmentComplition"), Constants.waitForEleLoad);
            try {
                sleep(800);
                if (driver.switchTo().alert().getText().contains("Workitem approved successfully")) {
                    driver.switchTo().alert().accept();
                    driver.switchTo().defaultContent();
                    configTestRunner.getChildTest().log(Status.PASS, "Staff Assignment completion is successfully.");
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Staff Assignment completion is not successfully.");
            } catch (Exception e) {

            }

        } else
            configTestRunner.getChildTest().log(Status.FAIL, "Selected matter is not opened successfully for staff approval.");
    }

    public void fnFindAssignWorkMandatoryStaffInbox(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Assigned Work In Mandatory Assignee Inbox"));
        List<String> fields = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("commisioner_Role").split(",")));
        List<String> fields1 = new ArrayList<>(Arrays.asList(configTestRunner.getTestData().get("staff_Role").split(",")));
        List<String> listFinal = new ArrayList<String>();
        listFinal.addAll(fields);
        listFinal.addAll(fields1);
        fnSelectTab(configTestRunner, "Work");
        for (String field : listFinal) {
            //Assign Role
            try {
                fnRoleSelection(configTestRunner, field);
                fnSelectTab(configTestRunner, "Work");
                //select the docket Inbox
                if (fnNewMatterStaffAssignment(configTestRunner, "Docket Inbox", "FullName", "Matter Number", "initialAdminRecord")) {
                    configTestRunner.getChildTest().log(Status.PASS, "Assign Work Item is present in the assignee inbox :" + field);
                    fnSearchDocumentAndView(configTestRunner);
                    break;

                }
            } catch (Exception e) {
                configTestRunner.getChildTest().log(Status.PASS, "Define role is not present in the list");
            }
        }
    }

    public boolean fnSearchDocumentAndView(ConfigTestRunner configTestRunner) {
        boolean viewFlag = false;
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id,'DockectMasterDetails.DocketMasterDetailsTemplate')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("btnDocumentSearch"), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnDocumentSearch"), Constants.waitForEleLoad);
            if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("documentTypeSelectAll"), Constants.waitForEleLoad)) {
                //select all check box for document type
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("documentTypeSelectAll"), Constants.waitForEleLoad);
                //Select All checkbox for onbehalf
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("onBehalfOf"), Constants.waitForEleLoad);
                //enter description
//                getWebElement("descriptionOfFiling").sendKeys(configTestRunner.getTestData().get("description"));
                //click on search button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("searchDocumentbtnSearch"), Constants.waitForEleLoad);
                if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("searchDocumentbtnViewAfterSearch"), Constants.waitForEleLoad)) {
                    //configTestRunner.getCommonMethods().waitAndClick(getWebElement("searchDocumentbtnViewAfterSearch"),Constants.waitForEleLoad);
                    //click on done button
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("doneButton"), Constants.waitForEleLoad);
                }

            }
        }
        driver.switchTo().defaultContent();
        return viewFlag;
    }

    public void fnapproveDistList(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Approve Distribution list"));
        sleep(2000);
        fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("DistributionListApproveList"), Constants.waitForEleLoad)) {
            getWebElement("DistributionListComment").sendKeys("Distribution List Approved");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("DistributionListApproveList"), Constants.waitForEleLoad);
            try {
                sleep(600);
                if (driver.switchTo().alert().getText().contains("Distribution List Approved Successfully")) {
                    driver.switchTo().alert().accept();
                    configTestRunner.getChildTest().log(Status.PASS, "Distribution List Approved Successfully");
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Distribution List is not Approved Successfully.");
            } catch (Exception e) {
                configTestRunner.getChildTest().log(Status.FAIL, "Distribution List is not Approved Successfully.");
            }

        }
        driver.switchTo().defaultContent();
    }

    public void fnAddNewMemberToDisList(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Add New Member to Distribution list"));
        fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("addNewDisMember"), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("addNewDisMember"), Constants.waitForEleLoad);

        }
        driver.switchTo().defaultContent();
    }

    public void fnAddOfficeForContested(ConfigTestRunner configTestRunner) {
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("AddNewMemPaty"), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("AddNewMemPaty"), Constants.waitForEleLoad);
            configTestRunner.getChildTest().log(Status.PASS, "OFFICE OF CONSUMER COUNSEL selected is Party for contested matter");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("doneButton"), Constants.waitForEleLoad);
        } else
            configTestRunner.getChildTest().log(Status.FAIL, "OFFICE OF CONSUMER COUNSEL selected is not Party for contested matter");
    }

    public void fnPublishDistList(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Publish Distribution list"));
        fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("PublishListComment"), Constants.waitForEleLoad)) {
            getWebElement("PublishListComment").sendKeys("Distribution List Approved");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("PublishListApproveList"), Constants.waitForEleLoad);
            try {
                sleep(500);
                if (driver.switchTo().alert().getText().contains("Distribution List Published Successfully")) {
                    driver.switchTo().alert().accept();
                    configTestRunner.getChildTest().log(Status.PASS, "Distribution List Published Successfully");

                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Distribution List is not Published Successfully.");
            } catch (Exception e) {

            }

        }
        driver.switchTo().defaultContent();
    }
    public void fnConfirmSchedule(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Publish Distribution list"));
        fnWaitForVisibility((driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]"))), Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'IBMFormIframe')]")));
        if (configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("confirmSchedulebtn"), Constants.waitForEleLoad)) {
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("confirmSchedulebtn"), Constants.waitForEleLoad);
            try {
                sleep(500);
                if (driver.switchTo().alert().getText().contains("Scheduling is confirm successfully Successfully")) {
                    driver.switchTo().alert().accept();
                    configTestRunner.getChildTest().log(Status.PASS, "Scheduling is confirm Successfully");

                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Scheduling is not confirm Published Successfully.");
            } catch (Exception e) {

            }

        }
        driver.switchTo().defaultContent();
    }

    public void fnMenuSelection(ConfigTestRunner configTestRunner, String menu, String subMenu) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Select " + menu + " and the submenu:" + subMenu));
        fnWaitForVisibility(configTestRunner.getCommonMethods().getspanWithText(configTestRunner, menu), Constants.waitForEleLoad);
        configTestRunner.getCommonMethods().fnMoveToElement(configTestRunner.getCommonMethods().getspanWithText(configTestRunner, menu));
        fnWaitForVisibility(configTestRunner.getCommonMethods().gettdWithText(configTestRunner, subMenu), Constants.waitForEleLoad);
        WebElement newMatterLink = configTestRunner.getCommonMethods().gettdWithText(configTestRunner, subMenu);
        configTestRunner.getCommonMethods().waitAndClick(newMatterLink, 30);
        configTestRunner.getChildTest().log(Status.INFO, "click on " + menu + "and submenu " + subMenu + " link successfully.");


    }

    public void fnSelectExternalUser(ConfigTestRunner configTestRunner) {
        sleep(1000);
        fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        fnWaitForVisibility(getWebElement("nextButton"), Constants.waitForEleLoad);
        //Select the On behalf of Radio button or Recommanded agency
        if (configTestRunner.getTestData().get("onBehalfOfExternalUser").equalsIgnoreCase("Yes")) {

            if (fnWaitForVisibility(getWebElement("externalUserRadio"), Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("externalUserRadio"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.INFO, "Filing on Behalf of External User radio button is selected successfully.");
            }
        }
        if (configTestRunner.getTestData().get("externalAgency").equalsIgnoreCase("Yes")) {
            if (fnWaitForVisibility(getWebElement("recomandedByAgencyRadio"), Constants.waitForEleLoad)) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("recomandedByAgencyRadio"), Constants.waitForEleLoad);
                configTestRunner.getChildTest().log(Status.INFO, "Recommended by Agency radio button is selected successfully.");
            }
        }
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"), Constants.waitForEleLoad);
        driver.switchTo().defaultContent();
    }

    public void fnSelectMatterType(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Select Matter Type & Sub Type"));
        sleep(2000);
        fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        getWebElement("matterType").clear();
        sleep(500);
        driver.switchTo().alert().accept();
        configTestRunner.getCommonMethods().setdataSendKeysEnter(getWebElement("matterType"), configTestRunner.getTestData().get("matterType"));
        configTestRunner.getChildTest().log(Status.INFO, "MatteyType selected is :" + configTestRunner.getTestData().get("matterType"));
        getWebElement("matterSubType").clear();
        sleep(500);
        driver.switchTo().alert().accept();
        configTestRunner.getCommonMethods().setdataSendKeysEnter(getWebElement("matterSubType"), configTestRunner.getTestData().get("MatterSubType"));
        configTestRunner.getChildTest().log(Status.INFO, "MatteySubType selected is :" + configTestRunner.getTestData().get("MatterSubType"));
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"), Constants.waitForEleLoad);
        driver.switchTo().defaultContent();
    }
    public void onBelfOfNext(ConfigTestRunner configTestRunner){
        sleep(2000);
        fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[contains(@id,'FilterCriteria_Section4')])[2]")));
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonnew"), Constants.waitForEleLoad);
        driver.switchTo().defaultContent();
    }
    public void fnSelectCompany(ConfigTestRunner configTestRunner){
        try {
            sleep(2000);
            fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")), Constants.waitForEleLoad);
            driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
            driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[contains(@id,'FilterCriteria_Section4')])[2]")));
            if (getWebElement("FinancialTab", "Add_InstituteClientType").isDisplayed()) {
                //Institute Client Type
                configTestRunner.getCommonMethods().selectCombobox(getWebElement("FinancialTab", "Add_InstituteClientType"), configTestRunner, configTestRunner.getTestData().get("institutateName"));
                //Indivisual Name
                sleep(1000);
                configTestRunner.getCommonMethods().setdataSendKeysEnterkeyDown(getWebElement("FinancialTab", "Add_CompanyName"), configTestRunner.getTestData().get("financeTabAdd_CompanyName"));
                configTestRunner.getChildTest().log(Status.PASS, " Institute name is selected successfully.");
                //click on next button
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Select the company window is not selected successfully.");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButtonnew"), 10);
            driver.switchTo().defaultContent();
        }catch (Exception e){

        }
    }
    public void fnMAtterTitle(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("MatterTitle & Field By selection"));
        sleep(1000);
        fnWaitForVisibility(getWebElement("matterTitle"), Constants.waitForEleLoad);
        getWebElement("matterTitle").sendKeys(configTestRunner.getTestData().get("matterTitle"));
        //Click on search button
        try {
            if (fnWaitForVisibility(getWebElement("searchButton"), 20)) {
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("searchButton"), 30);
                driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@dojoattachpoint='iframe'])[2]")));
                fnWaitForVisibility(getWebElement("searchfname"), Constants.waitForEleLoad);
                getWebElement("searchfname").sendKeys(configTestRunner.getTestData().get("Fname"));
                configTestRunner.getChildTest().log(Status.INFO, "First NAme enter is :" + configTestRunner.getTestData().get("Fname"));
                getWebElement("searchlname").sendKeys(configTestRunner.getTestData().get("Lname"));
                configTestRunner.getChildTest().log(Status.INFO, "First NAme enter is :" + configTestRunner.getTestData().get("Lname"));
                //click on search button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnsearch"), 30);
                //check the record search
                Assert.assertTrue(getWebElement("tblRadioButton").isDisplayed());
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("tblRadioButton"), Constants.waitForEleLoad);
                //click on select button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("btnSelect"), Constants.waitForEleLoad);
                driver.switchTo().defaultContent();
                driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
                //click on next button
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"), Constants.waitForEleLoad);
            }
        }catch (Exception e){

        }

    }
    public void fnMAtterTitle_internal(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("MatterTitle & Field By selection"));
        sleep(1000);
        fnWaitForVisibility(getWebElement("matterTitle"), Constants.waitForEleLoad);
        getWebElement("matterTitle").sendKeys(configTestRunner.getTestData().get("matterTitle"));

        //click on next button
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("nextButton"), Constants.waitForEleLoad);

    }
    public void fnDescFileSelect(ConfigTestRunner configTestRunner){
        try{
            if(fnWaitForVisibility(getWebElement("descriptionPopUpMessage"),60)){
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("descriptionPopUpMessageOk"),Constants.waitForEleLoad);
            }
        }catch (Exception ex){

        }
        sleep(1000);
        fnWaitForVisibility(getWebElement("description"),Constants.waitForEleLoad);
        getWebElement("description").sendKeys(configTestRunner.getTestData().get("description"));
        sleep(1000);
        driver.switchTo().frame(getWebElement("attachmentFrame"));
        WebElement docType = getWebElement("documentType");
        configTestRunner.getCommonMethods().selectComboboxSendKeys(docType, configTestRunner, configTestRunner.getTestData().get("documentType"));
        configTestRunner.getChildTest().log(Status.PASS,"Document Type selected is: "+configTestRunner.getTestData().get("documentType"));
        sleep(1000);
        WebElement docSubType = getWebElement("documentSubType");
        configTestRunner.getCommonMethods().selectComboboxSendKeys(docSubType,configTestRunner,configTestRunner.getTestData().get("documentSubType"));
        configTestRunner.getChildTest().log(Status.PASS,"Document Sub Type selected is: "+configTestRunner.getTestData().get("documentSubType"));

        //Select public checkbox
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("securityCheckBoxPublic"),Constants.waitForEleLoad);
        //select documet
        WebElement browser =driver.findElement(By.cssSelector("input[type='file']"));
        String js = "arguments[0].style.height='auto';arguments[0].style.visibility='visible';";
        ((JavascriptExecutor)driver).executeScript(js,browser);
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\UploadDocument\\"+configTestRunner.getTestData().get("attachDocumentName");
        sleep(300);
        browser.sendKeys(filePath);

        sleep(300);
        //click on attachment document
        if(getWebElement("addButtonFileUpload").isEnabled()) {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement( "addButtonFileUpload"), Constants.AJAX_TIMEOUT);
            configTestRunner.getCommonMethods().setFocusClick(getWebElement( "addButtonFileUpload"));
        }
        try {
            configTestRunner.getCommonMethods().fnWaitForVisibility(getWebElement("documentTypeHeader"), Constants.AJAX_TIMEOUT);
            if (getWebElement("documentTypeHeader").isDisplayed())
                configTestRunner.getChildTest().log(Status.PASS, "Document is uploaded successfully.");
            else
                configTestRunner.getChildTest().log(Status.FAIL, "Document is not uploaded successfully.");
        }catch (Exception e) {

        }
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("documentNextbtn"),Constants.waitForEleLoad);
    }

    public void fnSelectAddress(ConfigTestRunner configTestRunner){
        sleep(2000);
        driver.switchTo().defaultContent();
        fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[contains(@id,'FilterCriteria_Section4')])[2]")));
        if (configTestRunner.getTestData().get("externalAgency").equalsIgnoreCase("Yes"))
            configTestRunner.getMatterCreation().fnSubscribeAggrement(configTestRunner);
        if (configTestRunner.getTestData().get("onBehalfOfExternalUser").equalsIgnoreCase("Yes")) {
            configTestRunner.getMatterCreation().fnSelectAddress(configTestRunner);
            configTestRunner.getMatterCreation().fnAddNewPhoneNo(configTestRunner);
            configTestRunner.getMatterCreation().fnselectExistingContactPerson(configTestRunner);
            configTestRunner.getMatterCreation().fnOnBehlfOf(configTestRunner);
        }
         configTestRunner.getMatterCreation().fnpartiningTo(configTestRunner,configTestRunner.getTestData().get("pertainingTo"));
        if(configTestRunner.getTestData().get("onBehalfOfExternalUser").equalsIgnoreCase("Yes"))
            configTestRunner.getMatterCreation().fnSameCompanySelect(configTestRunner);
        else {
            configTestRunner.getFinanceAssurance().fnAddFinInstitute_Internal(configTestRunner);
            configTestRunner.getMatterCreation().fnSelectAddress(configTestRunner);
            configTestRunner.getMatterCreation().fnAddNewPhoneNo(configTestRunner);
            configTestRunner.getMatterCreation().fnselectExistingContactPerson(configTestRunner);
        }
         configTestRunner.getMatterCreation().fnpartiningTo(configTestRunner,configTestRunner.getTestData().get("partiigToAfterCompany"));
         if(getWebElement("associatedFiling").isDisplayed())
             configTestRunner.getChildTest().log(Status.PASS,"Companies/Entities/Individuals Associated to this Filing window is opened successfully." );
        else
             configTestRunner.getChildTest().log(Status.FAIL,"Companies/Entities/Individuals Associated to this Filing window is not opened successfully." );
        //c lick on Next Button
        configTestRunner.getCommonMethods().waitAndClick(getWebElement("MatterFiling","nextButtonNew"),Constants.waitForEleLoad);
        //Select matter title & field by
        driver.switchTo().defaultContent();
        fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")),Constants.waitForEleLoad);
        driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[@data-dojo-attach-point='rootFrame'])[2]")));
        if(configTestRunner.getTestData().get("onBehalfOfExternalUser").equalsIgnoreCase("Yes"))
            fnMAtterTitle(configTestRunner);
        else
            fnMAtterTitle_internal(configTestRunner);
        //seletc document type
        fnDescFileSelect(configTestRunner);
        //matter information
        fnMatterInformation(configTestRunner);
        fnMatterConfirmation(configTestRunner);

        driver.switchTo().defaultContent();
    }
    public boolean fnMatterInformation(ConfigTestRunner configTestRunner){
        boolean returnFlg = false;
        sleep(1000);
        fnWaitForVisibility(getWebElement("matterInformation"),Constants.waitForEleLoad);
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
    public void fnMatterConfirmation(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Matter Confirmation Verification"));
        driver.switchTo().defaultContent();
        driver.switchTo().frame(getWebElement("InterAppFrame"));
        //click on submit button
        try{
            if(getWebElement("submitButton").isDisplayed())
                configTestRunner.getCommonMethods().waitAndClick(getWebElement("submitButton"),Constants.waitForEleLoad);

        }catch (Exception e){
//            if(getWebElement("MatterFiling","submitButtonNew").isDisplayed())
//                configTestRunner.getCommonMethods().sendKeysEnter(getWebElement("submitButtonNew"));
        }
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
    }
    public void manageServiceList(ConfigTestRunner configTestRunner){
        fnWaitForVisibility(getWebElement("InternalMatterFiling", "search_IframePuraDocket"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement("InternalMatterFiling", "search_IframePuraDocket"));
        fnWaitForVisibility(getWebElement("btnSearchIframe"), Constants.waitForEleLoad);
        driver.switchTo().frame(getWebElement( "btnSearchIframe"));
        fnWaitForVisibility(getWebElement( "manageServiceList"), Constants.waitForEleLoad);
        configTestRunner.getCommonMethods().waitAndClick(getWebElement( "manageServiceList"),Constants.waitForEleLoad);
        driver.switchTo().defaultContent();
        String parentWindowHandle = driver.getWindowHandle();
        String window=null;// save the current window handle.
        WebDriver popup = null;
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        for (int i=1 ;i<newTab.size();i++){
             window =driver.getWindowHandle();
            driver.switchTo().window(newTab.get(i));
        }
        if(fnWaitForVisibility(getWebElement("manageServList_name"),Constants.waitForEleLoad)){
            getWebElement("manageServList_name").sendKeys("Jyoti");
            getWebElement("manageServList_lastName").sendKeys("Dhage");
            //click on search button
            configTestRunner.getCommonMethods().setFocusClick(getWebElement("manageServList_btnSearch"));
//            if(fnWaitForVisibility(getWebElement("manageServList_addRepresentative"),Constants.waitForEleLoad)){
//                configTestRunner.getCommonMethods().waitAndClick(getWebElement("manageServList_addRepresentative"),Constants.waitForEleLoad);
//                //switch to frame
//                newTab = new ArrayList<String>(driver.getWindowHandles());
//                for (int j = 1; j <= newTab.size(); j++) {
//                    driver.switchTo().window(newTab.get(j));
//                    fnSelectCompany(configTestRunner);
//                    newTab = new ArrayList<String>(driver.getWindowHandles());
//                        for (int k = 1; k <= newTab.size(); k++) {
//                            driver.switchTo().window(newTab.get(k));
//                            //select party & inventory
//                            configTestRunner.getCommonMethods().waitAndClick(getWebElement("manageServList_Intervenor"),Constants.waitForEleLoad);
//                            configTestRunner.getCommonMethods().waitAndClick(getWebElement("ManageServiceList_Donebtn"),Constants.waitForEleLoad);
//                        }
//                }
//            }
            selectInventory_Party(configTestRunner,"Party");
            selectInventory_Party(configTestRunner,"Inventory");

        }


    }
    public void selectInventory_Party(ConfigTestRunner configTestRunner,String patryInven) {
        if(fnWaitForVisibility(getWebElement("manageServList_addRepresentative"),Constants.waitForEleLoad)){
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("manageServList_addRepresentative"),Constants.waitForEleLoad);
            //switch to frame
            ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
            for (int j = 2; j < newTab.size(); j++) {
                driver.switchTo().window(newTab.get(j));
                fnSelectCompany_ManageList(configTestRunner);
                newTab=null;
                newTab = new ArrayList<String>(driver.getWindowHandles());
                for (int k = 2; k < newTab.size(); k++) {
                    driver.switchTo().window(newTab.get(k));
                    driver.getTitle();
                    driver.getCurrentUrl();
                    //select party & inventory
                    if (!(patryInven.equalsIgnoreCase("Party"))) {
                        executeExtJsClick(driver,getWebElement("manageServList_Intervenor"));
                        getWebElement("manageServList_Intervenor").click();
                    } else{
//                        WebElement ele =getWebElement("manageServList_Party");
//                        fnmakeElementVisibleByxpath(driver,ele);
//                        ele.click();
                        configTestRunner.getCommonMethods().setFocusSelectbar(getWebElement("manageServList_Party"));
                    }
//                        configTestRunner.getCommonMethods().waitAndClick(getWebElement("manageServList_Party"),Constants.waitForEleLoad);
                    configTestRunner.getCommonMethods().waitAndClick(getWebElement("ManageServiceList_Donebtn"),Constants.waitForEleLoad);
                    newTab=null;
                }

            }
        }
    }
    public void fnSelectCompany_ManageList(ConfigTestRunner configTestRunner){
        try {
            sleep(2000);
            fnWaitForVisibility(driver.findElement(By.xpath("(//iframe[contains(@id,'selectRepresentingEntity')])[1]")), Constants.waitForEleLoad);
            driver.switchTo().frame(driver.findElement(By.xpath("(//iframe[contains(@id,'selectRepresentingEntity')])[1]")));
            if (getWebElement("FinancialTab", "Add_InstituteClientType").isDisplayed()) {
                //Institute Client Type
                configTestRunner.getCommonMethods().selectComboboxSendKeys(getWebElement("FinancialTab", "Add_InstituteClientType"), configTestRunner, configTestRunner.getTestData().get("institutateName"));
                //Indivisual Name
                sleep(1000);
                configTestRunner.getCommonMethods().selectComboboxLinkSendKeys(getWebElement("FinancialTab", "Add_CompanyName"), configTestRunner, configTestRunner.getTestData().get("financeTabAdd_CompanyName"));
                configTestRunner.getChildTest().log(Status.PASS, " Institute name is selected successfully.");
                //click on next button
            } else
                configTestRunner.getChildTest().log(Status.FAIL, "Select the company window is not selected successfully.");
            configTestRunner.getCommonMethods().waitAndClick(getWebElement("donebtnCompany"), 10);
            driver.switchTo().defaultContent();
        }catch (Exception e){

        }
    }

}