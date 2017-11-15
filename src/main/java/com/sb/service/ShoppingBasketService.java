package com.sb.service;


import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ShoppingBasketService {
    protected BigDecimal calculatePriceIncludingOfferAndDiscount(String productName, Long quantity) {
        BigDecimal totalPrice = new BigDecimal("0.00");
        switch (productName){
            case "Apple":
                totalPrice = calculateTotal(quantity,"0.35");
                break;
            case "Banana":
                totalPrice = calculateTotal(quantity,"0.20");
                break;
            case "Melons":
                quantity = (Math.floorMod(quantity, 2L) == 0)? (long)Math.round(quantity / 2):
                    (Math.floorDiv(quantity, 2L) + Math.floorMod(quantity, 2L));
                totalPrice = calculateTotal(quantity,"0.50");
                break;
            case "Limes":
                quantity= (Math.floorMod(quantity, 3L) == 0)? Math.floorDiv(quantity, 3L)*2 :
                        (Math.floorDiv(quantity, 3L)*2) + Math.floorMod(quantity, 3L);
                totalPrice = calculateTotal(quantity,"0.15");
                break;
            default:
                break;
        }
        return totalPrice;
    }

    private BigDecimal calculateTotal(Long quantity, String unitPriceStr) {
        BigDecimal unitPrice = new BigDecimal(unitPriceStr);
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    public Money calculateTotalCostOfItemsInBasket(List<String> basketItems) {
        Double totalPrice = basketItems.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .mapToDouble(e ->
                        (calculatePriceIncludingOfferAndDiscount(e.getKey(), e.getValue()).doubleValue())).sum();
        return Money.of(totalPrice, Monetary.getCurrency("GBP"));
    }
}
