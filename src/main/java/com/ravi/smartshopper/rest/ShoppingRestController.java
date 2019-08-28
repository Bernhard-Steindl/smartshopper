package com.ravi.smartshopper.rest;

import com.ravi.smartshopper.services.SmartShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ShoppingRestController {

    @Autowired
    private SmartShoppingService smartShoppingService;

    @GetMapping("/shop/{item}")
    public Map<Double, String> doShopping(@PathVariable String item) {

        return smartShoppingService.doShopping(item);
    }
}
