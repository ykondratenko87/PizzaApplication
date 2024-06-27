package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create order", description = "Создает новый заказ")
    @PostMapping
    public OrderResponse checkout(@RequestBody OrderRequest orderRequest) {
        log.info("Received order request: {}", orderRequest);
        return orderService.checkout(orderRequest);
    }

    @Operation(summary = "Get order by ID", description = "Получает заказ по ID")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        log.info("Get order by ID: {}", id);
        return orderService.getOrderById(id);
    }
}