package md.donesk.trendshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shippingMethodId;

    @NotBlank
    @Size(max = 50)
    private String methodName;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal cost;

    private String deliverySpeed;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
