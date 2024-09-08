package rt.service.order.retailorder;

import rt.service.order.retailorder.entity.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderServiceApi {
    OrderEntity createOrder(OrderEntity order);
    OrderEntity updateOrder(UUID orderId, OrderEntity updateOrder);
    boolean cancelOrder(UUID orderId);
    OrderEntity getOrder(UUID orderId);
    List<OrderEntity> getAllOrders();
}
