package rt.service.order.retailorder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    private @NotNull String description;
    private @NotNull LocalDate orderDate;
    private @NotNull String shippingAddress;
    private String billingAddress;
    private @NotNull BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private @NotNull OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private @NotNull PaymentStatus paymentStatus;
}
