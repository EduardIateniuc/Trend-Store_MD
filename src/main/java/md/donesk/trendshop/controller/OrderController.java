//package md.donesk.trendshop.controller;
//
//import jakarta.validation.Valid;
//import md.donesk.trendshop.dto.request.OrderRequest;
//import md.donesk.trendshop.dto.response.OrderPageResponse;
//import md.donesk.trendshop.dto.response.OrderResponse;
//import md.donesk.trendshop.model.Order;
//import md.donesk.trendshop.service.OrderService;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/trend/api/orders")
//@Validated
//public class OrderController {
//
//    private final OrderService orderService;
//
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @GetMapping(produces = "application/json")
//    public ResponseEntity<OrderPageResponse> getAllOrders(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Page<Order> orderPage = orderService.getAllOrders(page, size);
//        Page<OrderResponse> orderResponsePage = orderPage.map(orderService::toOrderResponse);
//
//        OrderPageResponse orderPageResponse = new OrderPageResponse(
//                orderResponsePage.getContent(),
//                orderResponsePage.getTotalPages(),
//                orderResponsePage.getTotalElements(),
//                page
//        );
//        return new ResponseEntity<>(orderPageResponse, HttpStatus.OK);
//    }
//
////    @PostMapping
////    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
////        Order order = orderService.placeOrder(orderRequest);
////        OrderResponse orderResponse = orderService.toOrderResponse(order);
////        return ResponseEntity.ok(orderResponse);
////    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
//        Order order = orderService.getOrderById(id);
//        OrderResponse orderResponse = orderService.toOrderResponse(order);
//        return ResponseEntity.ok(orderResponse);
//    }
//}
