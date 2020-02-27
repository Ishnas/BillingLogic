package com.BillingLogic.BillingLogic.controller;


import com.BillingLogic.BillingLogic.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/{customerId}/{itemId}")
    public void incrementItemQuantity(@PathVariable("customerId") String customerId, @PathVariable("itemId") String itemId){
        cartService.incrementItemQuantity(customerId, itemId);
    }


    @DeleteMapping("/{customerId}/{itemId}")
    public void decrementItemQuantity(@PathVariable("customerId") String customerId, @PathVariable("itemId") String itemId) {
        cartService.decrementItemQuantity(customerId, itemId);
    }

    @DeleteMapping("/{customerId}")
    public void clearCart(@PathVariable("customerId") String customerId){
        cartService.clearCart(customerId);
    }

    @RequestMapping("/{customerId}")
    public boolean isCartEmpty(@PathVariable("customerId") String customerId) {
        boolean hasItem = cartService.isCartEmpty(customerId);
        return hasItem;
    }
}
