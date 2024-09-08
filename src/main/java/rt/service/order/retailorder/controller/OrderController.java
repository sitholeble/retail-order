package rt.service.order.retailorder.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rt.service.order.retailorder.OrderServiceApi;
import rt.service.order.retailorder.entity.OrderEntity;

import java.util.UUID;

@RequestMapping("/orders")
@RestController
@Validated
@AllArgsConstructor
public class OrderController {

    @Autowired
    private final OrderServiceApi serviceApi;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createOrder(@RequestBody @Valid OrderEntity order) {
        serviceApi.createOrder(order);

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(order.getOrderId());
    }
}
