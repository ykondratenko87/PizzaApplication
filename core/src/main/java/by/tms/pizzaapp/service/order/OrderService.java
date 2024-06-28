package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
    void clearOrdersAndBaskets();
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByUserId(Long userId);
}