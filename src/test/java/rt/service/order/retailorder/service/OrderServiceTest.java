package rt.service.order.retailorder.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rt.service.order.retailorder.OrderServiceApi;
import rt.service.order.retailorder.entity.OrderEntity;
import rt.service.order.retailorder.exception.OrderNotFoundException;
import rt.service.order.retailorder.model.OrderStatus;
import rt.service.order.retailorder.model.PaymentStatus;
import rt.service.order.retailorder.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final int INVOKE_ONCE = 1;
    private static final String DESCRIPTION = "4 large pizzas";
    private static final String BILLING_ADDRESS = "2 Churchill Street, Salt River";
    private static final String SHIPPING_ADDRESS = "2 Churchill Street, Salt River";
    private static final OrderStatus ORDER_STATUS = OrderStatus.DELIVERED;
    private static final PaymentStatus PAYMENT_STATUS = PaymentStatus.PAID;
    private static final BigDecimal TOTAL_AMOUNT = BigDecimal.TEN;
    private static final LocalDate ORDER_DATE = LocalDate.now();

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @Captor
    private ArgumentCaptor<OrderEntity> orderCaptor;

    private OrderEntity buildOrderEntity() {
        return OrderEntity.builder()
                .orderId(UUID.randomUUID())
                .description(DESCRIPTION)
                .orderDate(ORDER_DATE)
                .billingAddress(BILLING_ADDRESS)
                .shippingAddress(SHIPPING_ADDRESS)
                .orderStatus(ORDER_STATUS)
                .paymentStatus(PAYMENT_STATUS)
                .totalAmount(TOTAL_AMOUNT)
                .build();
    }

    @Test
    void implementsOrderServiceApi() {
        assertTrue(OrderServiceApi.class
                .isAssignableFrom(OrderService.class));
    }

    @Test
    void whenCreateOrder_verifyRepositoryCall() {
        //given
        var order = buildOrderEntity();

        //when
        when(repository.save(any(OrderEntity.class)))
                .thenReturn(mock(OrderEntity.class));

        service.createOrder(order);

        //then
        verify(repository, times(INVOKE_ONCE))
                .save(order);
    }

    @Test
    void whenCreateOrder_returnOrderEntity() {
        //given
        var orderEntity = buildOrderEntity();

        //when
        when(repository.save(any(OrderEntity.class)))
                .thenReturn(mock(OrderEntity.class));

        var order = service.createOrder(orderEntity);

        //then
        assertNotNull(order);
    }

    @Test
    void whenUpdateOrder_andOrderIdNotFound_throwException() {
        var orderId = UUID.randomUUID();
        var orderEntity =  buildOrderEntity();

        var error = assertThrows(OrderNotFoundException.class,
                ()-> service.updateOrder(orderId, orderEntity));

        assertEquals("Order not found with Id" + orderId, error.getMessage());
    }

    @Test
    void whenUpdateOrder_verifyRepositoryIsCalled() {
        var orderId = UUID.randomUUID();
        var orderEntity =  buildOrderEntity();

        when(repository.findById(orderId))
                .thenReturn(Optional.of(orderEntity));

        service.updateOrder(orderId, orderEntity);

        verify(repository, times(INVOKE_ONCE))
                .save(orderEntity);
    }

    @Test
    void whenCancelOrder_verifyRepositoryIsCalled() {
        var orderId = UUID.randomUUID();
        var orderEntity =  buildOrderEntity();

        when(repository.findById(orderId))
                .thenReturn(Optional.of(orderEntity));

        when(repository.save(any(OrderEntity.class)))
                .thenReturn(orderEntity);

        orderEntity.setOrderStatus(OrderStatus.CANCELLED);

        boolean updatedOrder = service.cancelOrder(orderId);

        verify(repository, times(INVOKE_ONCE))
                .save(orderCaptor.capture());

        assertTrue(updatedOrder);
        assertEquals(OrderStatus.CANCELLED, orderCaptor.getValue().getOrderStatus());
    }

    @Test
    void whenGetOrder_andOrderIdIsValid_returnOrder() {
        var orderId = UUID.randomUUID();

        when(repository.findById(orderId))
                .thenReturn(Optional.of(mock(OrderEntity.class)));

        var order = service.getOrder(orderId);

        assertNotNull(order);

        verify(repository, times(INVOKE_ONCE))
                .findById(orderId);
    }

    @Test
    void whenGetAllOrders_returnListOfOrderEntities() {
        when(repository.findAll())
                .thenReturn(List.of(mock(OrderEntity.class)));

        var ordersList = service.getAllOrders();

        assertNotNull(ordersList);
        verify(repository, times(INVOKE_ONCE))
                .findAll();
    }
}
