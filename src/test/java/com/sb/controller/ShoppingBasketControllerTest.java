package com.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ShoppingBasketControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void testCalculateSoppingBasketTotalWhenWeHaveAllOffer() throws Exception{
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Apple");
        shoppingList.add("Banana");
        shoppingList.add("Apple");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Limes");
        Money money = Money.of(1.70, "GBP");
        testTotalAsExpected(shoppingList, money.toString());
    }

    private void testTotalAsExpected(List<String> shoppingList, String moneyStr) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange("/getTotal", HttpMethod.POST,
                new HttpEntity<Object>(shoppingList, headers), String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(moneyStr));
    }

    @Test
    public void testCalculateSoppingBasketTotalWhenWeHaveNotRequiredItems() throws Exception{
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Apple");
        shoppingList.add("Banana");
        shoppingList.add("Apple");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Melons");
        shoppingList.add("Limes");
        shoppingList.add("Limes");
        shoppingList.add("Strawberry");
        Money money = Money.of(1.70, "GBP");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange("/getTotal", HttpMethod.POST,
                new HttpEntity<Object>(shoppingList, headers), String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}
