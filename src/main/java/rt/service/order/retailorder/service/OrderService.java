package rt.service.order.retailorder.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rt.service.order.retailorder.OrderServiceApi;
import rt.service.order.retailorder.entity.OrderEntity;
import rt.service.order.retailorder.exception.OrderNotFoundException;
import rt.service.order.retailorder.model.OrderStatus;
import rt.service.order.retailorder.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService implements OrderServiceApi {

    private final OrderRepository repository;

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        return repository.save(order);
    }

    @Transactional
    @Override
    public OrderEntity updateOrder(UUID orderId, OrderEntity updateOrder) {
        var existingOrder = repository.findById(orderId)
                        .orElseThrow(()-> new OrderNotFoundException(orderId));

        existingOrder.setDescription(updateOrder.getDescription());
        existingOrder.setOrderDate(updateOrder.getOrderDate());
        existingOrder.setBillingAddress(updateOrder.getBillingAddress());
        existingOrder.setShippingAddress(updateOrder.getShippingAddress());
        existingOrder.setOrderStatus(updateOrder.getOrderStatus());
        existingOrder.setPaymentStatus(updateOrder.getPaymentStatus());
        existingOrder.setTotalAmount(updateOrder.getTotalAmount());

        return repository.save(existingOrder);
    }

    @Override
    public OrderEntity cancelOrder(UUID orderId) {
        var existingOrder = repository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(orderId));

        existingOrder.setOrderStatus(OrderStatus.CANCELLED);

        return repository.save(existingOrder);
    }

    @Override
    public OrderEntity getOrder(UUID orderId) {
        return repository.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return repository.findAll();
    }
}
