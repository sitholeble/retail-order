package rt.service.order.retailorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import rt.service.order.retailorder.OrderServiceApi;
import rt.service.order.retailorder.entity.OrderEntity;
import rt.service.order.retailorder.model.OrderStatus;
import rt.service.order.retailorder.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    private static final UUID ORDER_ID = UUID.randomUUID();
    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final BigDecimal TOTAL_AMOUNT = BigDecimal.TEN;
    private static final PaymentStatus PAYMENT_STATUS = PaymentStatus.PAID;
    private static final String SHIPPING_ADDRESS = "33 Hoffman Road";
    private static final String BILLING_ADDRESS = "2 7th Avenue";
    private static final String DESCRIPTION = "1x hp laptop";
    private static final OrderStatus ORDER_STATUS = OrderStatus.DELIVERED;

    @MockBean
    private OrderServiceApi serviceApi;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void isAnnotatedWithRestController() {
        assertTrue(OrderController.class
                .isAnnotationPresent(RestController.class));
    }

    @Test
    void isAnnotatedWithValidated() {
        assertNotNull(OrderController.class
                .getAnnotation(Validated.class));
    }

    @Test
    void whenCreateOrder_andOrderDateIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(null)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(ORDER_STATUS)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andTotalAmountIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(null)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(ORDER_STATUS)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andDescriptionIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(null)
                .orderStatus(ORDER_STATUS)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andShippingAddressIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(null)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(ORDER_STATUS)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andOrderStatusIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(null)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andPaymentStatusIsNull_throwException() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(null)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(OrderStatus.CANCELLED)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void whenCreateOrder_andAllArgumentsAreValid_returnStatusCreated() throws Exception {
        var order = OrderEntity.builder()
                .orderId(ORDER_ID)
                .orderDate(ORDER_DATE)
                .totalAmount(TOTAL_AMOUNT)
                .paymentStatus(PAYMENT_STATUS)
                .shippingAddress(SHIPPING_ADDRESS)
                .billingAddress(BILLING_ADDRESS)
                .description(DESCRIPTION)
                .orderStatus(OrderStatus.DELIVERED)
                .build();

        var serializedOrder = objectMapper.writeValueAsString(order);

        var request = post("/orders")
                .contentType("application/json")
                .content(serializedOrder);

        var result = mockMvc.perform(request);

        result.andExpect(status().isCreated()).andReturn();
    }
}
