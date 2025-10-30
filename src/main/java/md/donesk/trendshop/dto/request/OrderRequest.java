package md.donesk.trendshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long customerId;
    private int shippingAddressId;
    private int shippingMethodId;
    private int paymentMethodId;
    private List<OrderItemRequest> items;
}
