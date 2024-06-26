package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "getAllOrders")
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        return orderService.getAllOrders();
    }

    @Operation(summary = "getOrderById")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable long id) {
        log.info("Fetching order with id: {}", id);
        return orderService.getOrderById(id).orElse(null);
    }

    @Operation(summary = "createOrder")
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("Creating new order with data: {}", orderRequest);
        return orderService.createOrder(orderRequest);
    }

    @Operation(summary = "getOrdersByUserId")
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUserId(@PathVariable long userId) {
        log.info("Fetching orders for user with id: {}", userId);
        return orderService.getOrdersByUserId(userId);
    }

    @Operation(summary = "cancelOrder")
    @DeleteMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable long id) {
        log.info("Canceling order with id: {}", id);
        orderService.cancelOrder(id);
    }

    @Operation(summary = "updateOrder")
    @PutMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable long id, @Valid @RequestBody OrderRequest orderRequest) {
        log.info("Updating order with id: {}", id);
        return orderService.updateOrder(id, orderRequest);
    }
}