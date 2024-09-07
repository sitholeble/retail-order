package rt.service.order.retailorder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rt.service.order.retailorder.model.OrderStatus;
import rt.service.order.retailorder.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private String description;
    private LocalDate orderDate;
    private String shippingAddress;
    private String billingAddress;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
