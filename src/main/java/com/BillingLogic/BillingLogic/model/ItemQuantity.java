package com.BillingLogic.BillingLogic.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class ItemQuantity {
    private HashMap<String, Integer> itemIdQuantityMap = new HashMap<>();
}
