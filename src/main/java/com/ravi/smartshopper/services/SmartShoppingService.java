package com.ravi.smartshopper.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SmartShoppingService {

    Logger logger = LoggerFactory.getLogger(SmartShoppingService.class);

    public static void main(String[] args) {
        SmartShoppingService smartShoppingService = new SmartShoppingService();

        Map<Double, String> results = smartShoppingService.doShopping("Meswak tooth paste");
    }

    public Map<Double, String> doShopping(String itemToBuy) {

        Map<Double, String> results = new TreeMap<>();
        //System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver", "/opt/selenium/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://google.com.au");
        driver.manage().window().maximize();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(itemToBuy);
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.submit();
        driver.findElement(By.linkText("Shopping")).click();
        try {
            logger.trace("Sleeping for 1 second.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("Exception while sleeping: {}", e.getMessage());
        }
        List<WebElement> priceTags = driver.findElements(By.className("h1Wfwb"));
        List<WebElement> hyperLinks = driver.findElements(By.className("AGVhpb"));
        int i = 0;
        for (WebElement priceTag : priceTags) {
            try {
                String price = priceTag.getText().replace("$", "");
                List<String> tokensToClean = Arrays.asList("+", "(");
                for (String token : tokensToClean) {
                    if (price.contains(token)) {
                        price = getSubStringAfterCleaning(price, "+");
                    }
                }
                results.put(Double.valueOf(price), hyperLinks.get(i).getAttribute("href"));
                i++;
            } catch (Exception e) {
                logger.error("Exception:{}", e.getMessage());
            }
        }
        driver.close();

        return results;
    }

    private String getSubStringAfterCleaning(String price, String s) {

        price = price.substring(0, price.indexOf(s)).trim();
        return price;
    }
}