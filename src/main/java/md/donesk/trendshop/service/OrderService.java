package md.donesk.trendshop.service;


import md.donesk.trendshop.exception.OrderNotFoundException;
import md.donesk.trendshop.model.*;
import md.donesk.trendshop.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final CustomerRepository customerRepository;
//    private final SizeRepository sizeRepository;
//    private final ColorRepository colorRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, AddressRepository addressRepository, PaymentMethodRepository paymentMethodRepository, ShippingMethodRepository shippingMethodRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.customerRepository = customerRepository;

    }

    public Page<Order> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

//    @Transactional
//    public Order placeOrder(OrderRequest orderRequest) {
//        // Validate all products and sizes before processing
//        validateOrderItems(orderRequest.getItems());
//
//        Order order = new Order();
//
//        Customer customer = customerRepository.findById(1) // TODO: Get from JWT
//                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
//
//        Address addressOrder = addressRepository.findById(orderRequest.getShippingAddressId())
//                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
//
//        PaymentMethod paymentMethodOrder = paymentMethodRepository.findById(orderRequest.getPaymentMethodId())
//                .orElseThrow(() -> new EntityNotFoundException("Payment Method not found"));
//
//        ShippingMethod shippingMethodOrder = shippingMethodRepository.findById(orderRequest.getShippingMethodId())
//                .orElseThrow(() -> new EntityNotFoundException("Shipping Method not found"));
//
//        BigDecimal totalAmount = processOrderItems(order, orderRequest.getItems());
//
//        order.setCustomer(customer);
//        order.setShippingMethod(shippingMethodOrder);
//        order.setPaymentMethod(paymentMethodOrder);
//        order.setShippingAddress(addressOrder);
//        order.setOrderTotal(totalAmount.add(shippingMethodOrder.getCost()));
//        order.setOrderStatus(OrderStatus.PENDING.name());
//
//        return orderRepository.save(order);
//    }

//    private void validateOrderItems(List<OrderItemRequest> items) {
//        for (OrderItemRequest itemRequest : items) {
//            Product product = productRepository.findById(itemRequest.getProductId())
//                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemRequest.getProductId()));
//
//            Size size = sizeRepository.findById(itemRequest.getSizeId())
//                    .orElseThrow(() -> new EntityNotFoundException("Size not found: " + itemRequest.getSizeId()));
//
//            Color color = colorRepository.findById(itemRequest.getColorId())
//                    .orElseThrow(() -> new EntityNotFoundException("Color not found: " + itemRequest.getColorId()));
//
//            if (!inventoryService.checkStockAvailability(
//                    itemRequest.getProductId(),
//                    itemRequest.getSizeId(),
//                    itemRequest.getColorId(),
//                    itemRequest.getQuantity())) {
//                throw new IllegalStateException(
//                        String.format("Insufficient stock for product %s in size %s and color %s",
//                                product.getProductName(),
//                                size.getValue(),
//                                color.getValue())
//                );
//            }
//        }
//    }

//    private BigDecimal processOrderItems(Order order, List<OrderItemRequest> items) {
//        BigDecimal totalAmount = BigDecimal.ZERO;
//
//        for (OrderItemRequest itemRequest : items) {
//            Product product = productRepository.findById(itemRequest.getProductId())
//                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//
//            Size size = sizeRepository.findById(itemRequest.getSizeId())
//                    .orElseThrow(() -> new EntityNotFoundException("Size not found"));
//
//            Color color = colorRepository.findById(itemRequest.getColorId())
//                    .orElseThrow(() -> new EntityNotFoundException("Color not found"));
//
//            boolean stockReduced = inventoryService.reduceStock(
//                    itemRequest.getProductId(),
//                    itemRequest.getSizeId(),
//                    itemRequest.getColorId(),
//                    itemRequest.getQuantity()
//            );
//
//            if (!stockReduced) {
//                throw new IllegalStateException("Failed to reduce stock for product: " + product.getProductName());
//            }
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setSize(size);
//            orderItem.setColor(color);
//            orderItem.setQuantity(itemRequest.getQuantity());
//            orderItem.setProductPrice(product.getPrice().multiply(new BigDecimal(itemRequest.getQuantity())));
//            orderItem.setOrder(order);
//
//            order.getItems().add(orderItem);
//            totalAmount = totalAmount.add(orderItem.getProductPrice());
//        }
//
//        return totalAmount;
//    }
//
//    public OrderResponse toOrderResponse(Order order) {
//        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
//                .map(item -> new OrderResponse.OrderItemResponse(
//                        item.getProduct().getProductId(),
//                        item.getSize().getId(),
//                        item.getColor().getId(),
//                        item.getQuantity(),
//                        item.getProductPrice()
//                ))
//                .toList();
//
//        return new OrderResponse(
//                order.getOrderId(),
//                order.getCustomer(),
//                order.getOrderStatus(),
//                order.getShippingAddress(),
//                order.getShippingMethod(),
//                order.getPaymentMethod(),
//                order.getOrderTotal(),
//                order.getCreatedAt(),
//                order.getUpdatedAt(),
//                itemResponses
//        );
//    }

}
