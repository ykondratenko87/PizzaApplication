package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.*;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(OrderRequest orderRequest);

    OrderResponse getOrderById(Long id);

    void clearOrdersAndBaskets();

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUserId(Long userId);
}