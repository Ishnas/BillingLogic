package com.BillingLogic.BillingLogic.service;

import com.BillingLogic.BillingLogic.dao.RedisDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {

    @Autowired
    private RedisDAO redisDAO;

    public void incrementItemQuantity(String customerId, String itemId) {
        try {
            redisDAO.incrementAndSave(customerId, itemId);
            log.info("customerId received = {}", customerId);

        } catch (Exception e){
            log.error("Problem = {}", e);
        }
    }

    public void decrementItemQuantity(String customerId, String itemId) {
        try {
            redisDAO.decrementAndSave(customerId, itemId);
            log.info("itemId received which is reduced = {}", itemId);
        } catch (Exception e){
            log.error("Problem = {}", e);
        }
    }

    public void clearCart(String customerId) {
        try {
            redisDAO.clearCart(customerId);
            log.info("Cart of {} is cleared", customerId);
        } catch (Exception e){
            log.error("Problem = {}", e);
        }
    }

    public boolean isCartEmpty(String customerId) {
        boolean hasItem = false;
        try {
            System.out.println("hello");
            hasItem = redisDAO.isCartEmpty(customerId);
            log.info("CustomerId whose cart is checking = {}", customerId);
        }catch (Exception e) {
            log.error("Problem = {}", e);
        }
        return hasItem;
    }
}
