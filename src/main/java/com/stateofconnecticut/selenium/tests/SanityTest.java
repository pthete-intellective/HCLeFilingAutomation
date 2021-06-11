package com.stateofconnecticut.selenium.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.stateofconnecticut.selenium.utilities.ConfigTestRunner;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SanityTest {
    private   String destFile;
    //public WebDriver
   private  ExtentReports extent;
    private ConfigTestRunner configTestRunnerLocal;

    @BeforeSuite
    private void extentReportconfig() throws IOException {
        ExtentHtmlReporter htmlReports;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        destFile = "src/main/resources/Reports/eZFile_Automation_"+dateFormat.format(new Date());
//        destFile = "eZFile_Automation_"+dateFormat.format(new Date());
        File newFolder =  new File(destFile);
        boolean created =  newFolder.mkdir();
        if(created) System.out.println("Folder is created !");
        else
            System.out.println("Unable to create folder");
        String destDir = "eZFile_Result_"+dateFormat.format(new Date()) + ".html";
        htmlReports = new ExtentHtmlReporter(destFile + "\\" +destDir);
        extent = new ExtentReports();
        extent.attachReporter(htmlReports);
        htmlReports.config().setReportName("eZFile Automation Test Result");
        htmlReports.config().setTheme(Theme.STANDARD);
        htmlReports.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReports.config().setDocumentTitle("eZFile_SanityTestResult");
    }
    @Test
    public void createMatterTC001(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0001");
    }
    @Test
    public void createMatterTC002(){

        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0002");
    }
    @Test
    public void createMatterTC003(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0003");
    }
    @Test
    public void createMatterTC004(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0004");
    }
    @Test
    public void createMatterTC005(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0005");
    }
    @Test
    public void createMatterTC006(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0006");
    }
    @Test
    public void createMatterTC007(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0007");
    }
    @Test
    public void createMatterTC008(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0008");
    }
    @Test
    public void createMatterTC009(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0009");
    }
    @Test
    public void createMatterTC010(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0010");
    }
    @Test
    public void createMatterTC011(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0011");
    }
    @Test
    public void createMatterTC012(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0012");
    }
    @Test
    public void createMatterTC013(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0013");
    }
    @Test
    public void createMatterTC014(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0014");
    }
    @Test
    public void createMatterTC015(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0015");
    }
    @Test
    public void createMatterTC016() {
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0016");
    }
    @Test
    public void createMatterTC017() {
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0017");
    }
    @Test
    public void createMatterTC018() {
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0018");
    }
    @Test
    public void createMatterTC019() {
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0019");
    }
    @Test
    public void createMatterTC020() {
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC0020");
    }
        @AfterSuite
    public void End_SetUp(){

        extent.flush();
        getConfigTestRunnerLocal().getDriver().quit();
    }

    @AfterMethod
    public void fnCloseBrowser(ITestResult testResult){

         if(testResult.getStatus()== ITestResult.FAILURE){
            getConfigTestRunnerLocal().getChildTest().log(Status.FAIL, MarkupHelper.createLabel("Test failed because of below reason", ExtentColor.INDIGO));
            getConfigTestRunnerLocal().getChildTest().log(Status.FAIL,testResult.getThrowable());
            String name ="Test_Failuar_ScreenShot";
            try {
                getConfigTestRunnerLocal().getChildTest().log(Status.INFO, "Failuar Reason is" + getConfigTestRunnerLocal().getChildTest().addScreenCaptureFromPath(getConfigTestRunnerLocal().screenShotName(name)));
//                getConfigTestRunnerLocal().getChildTest().log(Status.PASS, "Matter is created successfully " + getConfigTestRunnerLocal().getChildTest().addScreenCaptureFromPath(getConfigTestRunnerLocal().capture(name)));
            }catch (Exception exc){
                exc.printStackTrace();
            }
            getConfigTestRunnerLocal().getDriver().quit();
        }
        else if(testResult.getStatus() == ITestResult.SKIP){
            getConfigTestRunnerLocal().getChildTest().log(Status.SKIP,testResult.getThrowable());
             getConfigTestRunnerLocal().getDriver().quit();
        }else if(testResult.getStatus() == ITestResult.SUCCESS){

             getConfigTestRunnerLocal().data.setCellData("Pass",getConfigTestRunnerLocal().data.rowValue( getConfigTestRunnerLocal().getTestCase().get("SR_NO"), getConfigTestRunnerLocal().data.ColumnValue("SR_NO","TestCase")),getConfigTestRunnerLocal().data.ColumnValue("Status","TestCase"));
         }
//        else
          //  getConfigTestRunnerLocal().getDriver().quit();
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extent) {
        this.extent = extent;
    }

    public ConfigTestRunner getConfigTestRunnerLocal() {
        return configTestRunnerLocal;
    }

    public void setConfigTestRunnerLocal(ConfigTestRunner configTestRunnerLocal) {
        this.configTestRunnerLocal = configTestRunnerLocal;
    }
}

