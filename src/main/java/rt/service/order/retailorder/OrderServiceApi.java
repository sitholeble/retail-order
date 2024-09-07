package rt.service.order.retailorder;

import rt.service.order.retailorder.entity.OrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderServiceApi {
    OrderEntity createOrder(OrderEntity order);
    OrderEntity updateOrder(UUID orderId, OrderEntity updateOrder);
    OrderEntity cancelOrder(UUID order);
    OrderEntity getOrder(UUID order);
    List<OrderEntity> getAllOrders();
}
