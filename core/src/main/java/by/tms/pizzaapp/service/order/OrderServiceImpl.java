package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.*;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.order.*;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.exception.*;
import by.tms.pizzaapp.mapper.OrderMapper;
import by.tms.pizzaapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final BasketRepository basketRepository;
    private final PizzaRepository pizzaRepository;
    private final CustomPizzaRepository customPizzaRepository;

    @Override
    public OrderResponse checkout(OrderRequest orderRequest) {
        if (!userRepository.existsById(orderRequest.getUserId())) {
            throw new ApplicationExceptions.UserNotFoundException("User not found");
        }
        Order currentOrder = orderRepository.findByUserIdAndStatus(orderRequest.getUserId(), OrderStatus.ORDERING)
                .orElseThrow(() -> new ApplicationExceptions.OrderNotFoundException("Current ordering order not found"));
        currentOrder.setAddress(orderRequest.getAddress());
        currentOrder.setStatus(OrderStatus.IN_DELIVERY);
        currentOrder.setTotalPrice(orderRequest.getTotalPrice());
        currentOrder.setOrderDate(orderRequest.getOrderDate());
        List<Pizza> pizzas = orderRequest.getPizzaIds().stream()
                .map(id -> pizzaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Pizza not found: " + id)))
                .collect(Collectors.toList());
        currentOrder.setPizzas(pizzas);
        orderRepository.save(currentOrder);
        customPizzaRepository.deleteByUserId(orderRequest.getUserId());
        basketRepository.deleteByUserId(orderRequest.getUserId());
        return orderMapper.toResponse(currentOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponse(order);
    }

    @Override
    public void clearOrdersAndBaskets() {
        List<Order> orderingOrders = orderRepository.findByStatus(OrderStatus.ORDERING);
        orderingOrders.forEach(order -> {
            Long userId = order.getUser().getId();
            Basket basket = basketRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Basket not found"));
            basket.getPizzas().forEach(pizza -> {
                pizza.setQuantity(pizza.getQuantity() + 1);
                pizzaRepository.save(pizza);
            });
            customPizzaRepository.deleteByUserId(userId);
            basketRepository.deleteByUserId(userId);
            orderRepository.delete(order);
        });
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }
}