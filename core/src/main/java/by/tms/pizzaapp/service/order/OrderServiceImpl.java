package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import by.tms.pizzaapp.exception.OrderNotFoundException;
import by.tms.pizzaapp.exception.UserNotFoundException;
import by.tms.pizzaapp.mapper.OrderMapper;
import by.tms.pizzaapp.mapper.PizzaMapper;
import by.tms.pizzaapp.repository.OrderRepository;
import by.tms.pizzaapp.repository.PizzaRepository;
import by.tms.pizzaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final PizzaRepository pizzaRepository;

    @Override
    public OrderResponse checkout(OrderRequest orderRequest) {
        // Проверяем, существует ли пользователь
        if (!userRepository.existsById(orderRequest.getUserId())) {
            throw new UserNotFoundException("User not found");
        }

        // Ищем текущий заказ со статусом ORDERING для пользователя
        Order currentOrder = orderRepository.findByUserIdAndStatus(orderRequest.getUserId(), OrderStatus.ORDERING)
                .orElseThrow(() -> new OrderNotFoundException("Current ordering order not found"));

        // Обновляем адрес и статус заказа
        currentOrder.setAddress(orderRequest.getAddress());
        currentOrder.setStatus(OrderStatus.IN_DELIVERY);
        currentOrder.setTotalPrice(orderRequest.getTotalPrice());
        currentOrder.setOrderDate(orderRequest.getOrderDate());

        // Сохраняем изменения
        orderRepository.save(currentOrder);

        return orderMapper.toResponse(currentOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponse(order);
    }
}