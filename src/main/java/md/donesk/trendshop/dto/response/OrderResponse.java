package md.donesk.trendshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.donesk.trendshop.model.Address;
import md.donesk.trendshop.model.Customer;
import md.donesk.trendshop.model.PaymentMethod;
import md.donesk.trendshop.model.ShippingMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Customer customer;
    private String orderStatus;
    private Address shippingAddress;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private BigDecimal orderTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponse> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private Long sizeId;
        private Integer colorId;
        private Integer quantity;
        private BigDecimal productPrice;
    }
}