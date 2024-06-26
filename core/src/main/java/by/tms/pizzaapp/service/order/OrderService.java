package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderResponse> getAllOrders();

    Optional<OrderResponse> getOrderById(long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrdersByUserId(long userId);

    void cancelOrder(Long orderId);

    OrderResponse updateOrder(Long orderId, OrderRequest orderRequest);
}