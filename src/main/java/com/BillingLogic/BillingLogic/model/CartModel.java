package com.BillingLogic.BillingLogic.model;

import lombok.Data;

@Data
public class CartModel {

    //private int cartNumber;
    private String customerId;
    private String itemId;
    //private String itemName;
    private int itemQuantity;
    private int itemPrice;
    //private float discountPrice;

    public CartModel(String customerId, String itemId, int itemQuantity, int itemPrice){
        this.customerId = customerId;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }
}
