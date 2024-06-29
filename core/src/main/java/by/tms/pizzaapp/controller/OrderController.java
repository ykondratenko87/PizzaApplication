package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.order.*;
import by.tms.pizzaapp.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Endpoints for managing order controller")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create order", description = "Создает новый заказ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse checkout(@RequestBody OrderRequest orderRequest) {
        log.info("Received order request: {}", orderRequest);
        return orderService.checkout(orderRequest);
    }

    @Operation(summary = "Get order by ID", description = "Получает заказ по ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long id) {
        log.info("Get order by ID: {}", id);
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Clear orders and baskets", description = "Удаляет все заказы со статусом ORDERING и связанные с ними корзины")
    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearOrdersAndBaskets() {
        log.info("Clearing all orders and baskets");
        orderService.clearOrdersAndBaskets();
    }

    @Operation(summary = "Get all orders", description = "Получает все заказы")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        log.info("Get all orders");
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get orders by user ID", description = "Получает заказы по ID пользователя")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByUserId(@PathVariable Long userId) {
        log.info("Get orders by user ID: {}", userId);
        return orderService.getOrdersByUserId(userId);
    }
}