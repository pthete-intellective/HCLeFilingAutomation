package com.stateofconnecticut.selenium.tests;

import org.testng.TestNG;

public class TestRunner {
    static TestNG testNg;
    public static void main(String[] args){
        testNg = new TestNG();
        testNg.setTestClasses(new Class[] {SanityTest.class});
        testNg.run();
    }
}
