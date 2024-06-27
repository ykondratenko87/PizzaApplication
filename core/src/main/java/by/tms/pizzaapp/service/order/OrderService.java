package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;

public interface OrderService {
    OrderResponse checkout(OrderRequest orderRequest);
    OrderResponse getOrderById(Long id);
}