package rt.service.order.retailorder.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rt.service.order.retailorder.OrderServiceApi;
import rt.service.order.retailorder.entity.OrderEntity;

import java.util.List;
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

    @GetMapping(value = "/{order_id}/order",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderEntity> getOrder(@PathVariable("order_id") UUID orderId) {
         var orderResponse = serviceApi.getOrder(orderId);

         return ResponseEntity.ok(orderResponse);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        var orderListResponse = serviceApi.getAllOrders();

        return ResponseEntity.ok(orderListResponse);
    }

    @DeleteMapping(value = "/{order_id}/cancel",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelOrder(@PathVariable("order_id") UUID orderId) {
        boolean isCancelled = serviceApi.cancelOrder(orderId);

        return isCancelled
                ? ResponseEntity.ok("Order canceled successfully.")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order could not be canceled.");
    }

    @PutMapping(value = "/{order_id}/order",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable("order_id") UUID orderId, OrderEntity orderEntity) {

        var order = serviceApi.updateOrder(orderId, orderEntity);

        return ResponseEntity.ok(order);
    }
}
