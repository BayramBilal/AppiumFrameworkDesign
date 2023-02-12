package Appium;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.Android.FormPage;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerce_tc_4_HybriDataProvider extends BaseTest {


    @Test (dataProvider = "getData")
    public void FillForm(String name, String gender, String country) throws InterruptedException {

        FormPage formPage = new FormPage(driver);
        formPage.setNameField(name);
        formPage.setGender(gender);
        formPage.setCountrySelection(country);
        formPage.submitForm();

//        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
//        productCatalogue.addItemToCartByIndex(0);
//        productCatalogue.addItemToCartByIndex(0);
//
//        productCatalogue.goToCartPage();

//    driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Eva Peron");
//    driver.hideKeyboard();
//    driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
//    driver.findElement(By.id("android:id/text1")).click();
//    driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));"));
//    driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
//
//    driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));"));

//    driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CHART']")).get(0).click();
//    driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CHART']")).get(0).click();

        int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for (int i = 0; i < productCount; i++) {
            String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if(productName.equalsIgnoreCase("Jordan 6 Rings")){
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
            if(productName.equalsIgnoreCase("Jordan Lift Off")){
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }
        }

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));
        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = productPrices.size();
        double totalSum = 0;

        for (int i = 0; i < count; i++) {
            String amountString = productPrices.get(i).getText();
            //$160.97
            Double price = getFormattedAmount(amountString);
            totalSum = totalSum + price;   //160.97 + 120 = 280.97
        }
        String displaySum = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double displatFormattedSum = getFormattedAmount(displaySum);
        System.out.println( totalSum+" = " + displatFormattedSum);
        Assert.assertEquals(totalSum, displatFormattedSum);

        longPressAction(driver.findElement(By.id("com.androidsample.generalstore:id/termsButton")));
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000);

        //Hybrid app -->Google page -->

        Set<String> contexts = driver.getContextHandles();
        for (String contextName : contexts) {
            System.out.println(contextName);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore"); // need chrome driver
        driver.findElement(By.name("q")).sendKeys("rahul shetty academy", Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");

    }

    @DataProvider
    public Object [][] getData(){

        return new Object [][] { {"Eva Peron","female","Argentina"},{"Muller","male","Germany"},{"selim","male","Turkey"} };

    }
}
