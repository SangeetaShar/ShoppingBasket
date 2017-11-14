package com.sb.controller;

import com.sb.service.ShoppingBasketService;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ShoppingBasketController {
    static private String[] allowedItems= {"Apple", "Banana", "Melons", "Limes"};

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @RequestMapping(method = RequestMethod.POST, path="/getTotal")
    public ResponseEntity<?> calculateTotalForBasket(@RequestBody List<String> basketItems){
        if(validateShoppingBasket(basketItems)) {
            Money totalCost = shoppingBasketService.calculateTotalCostOfItemsInBasket(basketItems);
            return ResponseEntity.ok(totalCost.toString());
        } else{
            return new ResponseEntity<>("Basket contains unwantedItems", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean validateShoppingBasket(@RequestBody List<String> basketItems) {
        return basketItems.parallelStream().allMatch(name -> Arrays.asList(allowedItems).contains(name));
    }

}
