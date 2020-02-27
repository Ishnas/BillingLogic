package com.BillingLogic.BillingLogic.model;


import lombok.Data;

@Data
public class ItemModel {


    private String itemId;
    private Integer itemPrice;

    public ItemModel(String itemId, Integer itemPrice) {

        this.itemId = itemId;
        this.itemPrice = itemPrice;
    }
}
