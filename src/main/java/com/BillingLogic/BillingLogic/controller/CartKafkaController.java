package com.BillingLogic.BillingLogic.controller;


import com.BillingLogic.BillingLogic.model.CartModel;
import com.BillingLogic.BillingLogic.model.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Kafka")
public class CartKafkaController {

    @Autowired
    KafkaTemplate<String, CartModel> kafkaTemplate;
    @Autowired
    KafkaTemplate<String, ItemModel> kafkaTemplate2;
    private final String Topic1 = "Cart_Details";
    private final String Topic2 = "Item_Details";

    @PostMapping("/publish/{customerId}/{itemId}/{itemQuantity}/{itemPrice}")
    public String postToKafkaTopic(@PathVariable("customerId") String customerId, @PathVariable("itemId") String itemId, @PathVariable("itemQuantity") Integer itemQuantity, @PathVariable("itemPrice") Integer itemPrice){

        ListenableFuture<SendResult<String, CartModel>> future = kafkaTemplate.send(Topic1, customerId, new CartModel(customerId, itemId, itemQuantity, itemPrice));
        future.addCallback(new ListenableFutureCallback<SendResult<String, CartModel>>() {

            @Override
            public void onSuccess(SendResult<String, CartModel> result) {
                System.out.println("Sent message=[" + result.getProducerRecord().value() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + ex.getLocalizedMessage().isEmpty() + "] due to : " + ex.getMessage());
            }
        });

        return "Published Successfully";
    }


    @PostMapping("/publish/{itemId}/{itemPrice}")
    public String postItemToKafkaTopic(@PathVariable("itemId") String itemId, @PathVariable("itemPrice") Integer itemPrice){

        ListenableFuture<SendResult<String, ItemModel>> future = kafkaTemplate2.send(Topic2, itemId, new ItemModel(itemId, itemPrice));
        future.addCallback(new ListenableFutureCallback<SendResult<String, ItemModel>>() {

            @Override
            public void onSuccess(SendResult<String, ItemModel> result) {
                System.out.println("Sent message=[" + result.getProducerRecord().value() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + ex.getLocalizedMessage().isEmpty() + "] due to : " + ex.getMessage());
            }
        });

        return "Published Items Successfully";
    }
}
