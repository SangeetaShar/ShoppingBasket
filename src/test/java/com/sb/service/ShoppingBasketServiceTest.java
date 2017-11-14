package com.sb.service;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShoppingBasketServiceTest {

    private ShoppingBasketService shoppingBasketService;

    @Before
    public void setUp() throws Exception{
        shoppingBasketService = new ShoppingBasketService();
    }


    @Test
    public void testCalculateSoppingBasketTotalOnlyWithApple() throws Exception{
        testTotalAsExpected("Apple", 1, "0.35");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithMultipleApples() throws Exception{
        testTotalAsExpected("Apple", 3, "1.05");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithBanana() throws Exception{
        testTotalAsExpected("Banana",1, "0.20");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithMelon() throws Exception{
        testTotalAsExpected("Melons", 1,"0.50");
    }
    @Test
    public void testCalculateSoppingBasketTotalOnlyWithMelonsOffer() throws Exception{
        testTotalAsExpected("Melons", 2,"0.50");
    }
    @Test
    public void testCalculateSoppingBasketTotalOnlyWithMoreThenOfferedMelons() throws Exception{
        testTotalAsExpected("Melons", 3,"1.00");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithLime() throws Exception{
        testTotalAsExpected("Limes", 1,"0.15");
    }
    @Test
    public void testCalculateSoppingBasketTotalOnlyWith2Lime() throws Exception{
        testTotalAsExpected("Limes", 2,"0.30");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithLimesOffer() throws Exception{
        testTotalAsExpected("Limes", 3,"0.30");
    }

    @Test
    public void testCalculateSoppingBasketTotalOnlyWithMoreLimesOffer() throws Exception{
        testTotalAsExpected("Limes", 4,"0.45");
    }

    private void testTotalAsExpected(String productName, long quantity, String totalPrice) throws Exception{
        BigDecimal totalPriceAfterAllOffer = shoppingBasketService.calculatePriceIncludingOfferAndDiscount(productName, quantity);
        BigDecimal expectedPrice = new BigDecimal(totalPrice);
        assertThat(totalPriceAfterAllOffer, equalTo(expectedPrice));
    }

    @Test
    public void testCalculateTotalCostOfItemsInBasketTest(){
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Apple");
        shoppingList.add("Banana");
        shoppingList.add("Apple");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Limes");
        Money expectedTotalMoney = Money.of(1.70, "GBP");
        Money totalPriceAfterAllOffer = shoppingBasketService.calculateTotalCostOfItemsInBasket(shoppingList);
        assertThat(totalPriceAfterAllOffer, equalTo(expectedTotalMoney));
    }

    @Test
    public void testCalculateTotalCostOfItemsInBasketTestWithOffers(){
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Apple");
        shoppingList.add("Banana");
        shoppingList.add("Apple");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Melons");
        shoppingList.add("Apple");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Limes");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Limes");
        Money expectedTotalMoney = Money.of(2.85, "GBP");
        Money totalPriceAfterAllOffer = shoppingBasketService.calculateTotalCostOfItemsInBasket(shoppingList);
        assertThat(totalPriceAfterAllOffer, equalTo(expectedTotalMoney));
    }

}
