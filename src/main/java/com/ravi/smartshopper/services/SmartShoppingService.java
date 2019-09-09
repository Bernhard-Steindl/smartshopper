package com.ravi.smartshopper.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SmartShoppingService {

    Logger logger = LoggerFactory.getLogger(SmartShoppingService.class);

    public Map<Double, String> doShopping(String itemToBuy) throws InterruptedException {

        logger.debug("Shopping:{}", itemToBuy);
        Map<Double, String> results = new TreeMap<>();
        System.setProperty("webdriver.gecko.driver", "/opt/selenium/geckodriver");
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
        //TODO: Open country specific google website
        driver.get("https://google.com.au");
        driver.manage().window().maximize();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(itemToBuy);
        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.submit();
        driver.findElement(By.linkText("Shopping")).click();
        logger.trace("Sleeping for 1 second.");
        Thread.sleep(1000);
        List<WebElement> priceTags = driver.findElements(By.className("sh-dlr__list-result"));
        List<WebElement> hyperLinks = driver.findElements(By.className("sh-dlr__content"));
        int i = 0;
        for (WebElement priceTag : priceTags) {
            try {
                String price = getMatchingText("(\\d+\\.\\d{1,2})", priceTag.getText(), 0);
                String url = hyperLinks.get(i).findElement(By.tagName("a")).getAttribute("href");
                //NOSONAR String url = getMatchingText("(href=\\\"(.*?)\\\")", urlInput, 2);
                //NOSONAR logger.debug("url:" + url);
                results.put(Double.valueOf(price), url);
                i++;
            } catch (Exception e) {
                logger.error("Exception:{}", e.getMessage());
            }
        }
        driver.close();

        return results;
    }

    private String getMatchingText(String regex, String input, int groupToCapture) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(groupToCapture);
        }
        return "";
    }
}