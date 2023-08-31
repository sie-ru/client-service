package com.test.client.client;

import com.test.client.config.OrderClientConfiguration;
import com.test.client.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderService", url = "http://localhost:8002", configuration = OrderClientConfiguration.class)
public interface OrderClient {

    @GetMapping("/api/order")
    List<Order> getOrdersByClientId(@RequestParam("clientId") Long clientId);
}
