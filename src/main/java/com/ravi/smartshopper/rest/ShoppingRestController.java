package com.ravi.smartshopper.rest;

import com.ravi.smartshopper.services.SmartShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ShoppingRestController {
    Logger logger = LoggerFactory.getLogger(ShoppingRestController.class);

    @Autowired
    private SmartShoppingService smartShoppingService;

    @GetMapping("/shop/{item}")
    public Map<Double, String> doShopping(@PathVariable String item) {

        try {
            return smartShoppingService.doShopping(item);
        } catch (InterruptedException e) {
            logger.error("Exception in doShopping while sleeping...");
        }
        return new HashMap<>();
    }
}
