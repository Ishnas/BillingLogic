package com.BillingLogic.BillingLogic.dao;

import com.BillingLogic.BillingLogic.model.ItemQuantity;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RedisDAO {

    private RedissonClient client;

    public RedisDAO() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("127.0.0.1:6379");
        client = Redisson.create(config);
    }

    public void incrementAndSave(String customerId, String itemId) {
        RBucket<ItemQuantity> bucket = client.getBucket(customerId);
        ItemQuantity itemQuantity = bucket.get(); // null
        if(itemQuantity == null) {
            bucket.set(new ItemQuantity());
        }
        itemQuantity = bucket.get();
        HashMap<String, Integer> hm = itemQuantity.getItemIdQuantityMap();
        if(hm.containsKey(itemId)) {
            hm.put(itemId, hm.get(itemId) + 1);
        } else {
            hm.put(itemId, 1);
        }
        itemQuantity.setItemIdQuantityMap(hm);
        bucket.set(itemQuantity);
    }

    public void decrementAndSave(String customerId, String itemId) {
        RBucket<ItemQuantity> bucket = client.getBucket(customerId);
        ItemQuantity itemQuantity = bucket.get();
        HashMap<String, Integer> hm = itemQuantity.getItemIdQuantityMap();
        if(hm.containsKey(itemId)) {
            if(hm.get(itemId) >= 1) {
                hm.put(itemId, hm.get(itemId) - 1);
            }
        }
        itemQuantity.setItemIdQuantityMap(hm);
        bucket.set(itemQuantity);
    }

    public void clearCart(String customerId) {
        RBucket<ItemQuantity> bucket = client.getBucket(customerId);
        ItemQuantity itemQuantity = bucket.get();
        bucket.delete();
    }

    public boolean isCartEmpty(String customerId) {
        boolean hasItem = false;
        System.out.println("Hi");
        RBucket<ItemQuantity> bucket = client.getBucket(customerId);
        ItemQuantity itemQuantity = bucket.get();
        System.out.println("Hi");
        if(itemQuantity!= null) {
            Map<String, Integer> itemQuantityMap = itemQuantity.getItemIdQuantityMap();
            for (Map.Entry<String, Integer> item : itemQuantityMap.entrySet()) {
                if (item.getValue() > 0) {
                    hasItem = true;
                } else hasItem = false;
            }
        }
        return hasItem;
    }
}
