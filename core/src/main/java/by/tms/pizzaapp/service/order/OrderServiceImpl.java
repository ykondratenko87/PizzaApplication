package by.tms.pizzaapp.service.order;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import by.tms.pizzaapp.entity.user.User;
import by.tms.pizzaapp.mapper.OrderMapper;
import by.tms.pizzaapp.repository.BasketRepository;
import by.tms.pizzaapp.repository.OrderRepository;
import by.tms.pizzaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BasketRepository basketRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponse> getOrderById(long id) {
        return orderRepository.findById(id).map(orderMapper::toResponse);
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        try {
            Order order = orderMapper.toEntity(orderRequest);
            order.setStatus(OrderStatus.valueOf(orderRequest.getStatus()));
            order.setOrderDate(LocalDate.now());
            Order savedOrder = orderRepository.save(order);

            // Clear the basket
            List<Basket> baskets = basketRepository.findByOrdersId(order.getId());
            baskets.forEach(basket -> basket.getOrders().remove(order));
            basketRepository.saveAll(baskets);

            return orderMapper.toResponse(savedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create order. Please try again later.");
        }
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (orderList.isEmpty()) {
            throw new NoSuchElementException("No orders found for user with id: " + userId);
        }
        return orderList.stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
        } else {
            throw new NoSuchElementException("Order with id: " + orderId + " not found.");
        }
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setPizzaPrice(orderRequest.getPizzaPrice());
            order.setOrderDate(orderRequest.getOrderDate());
            order.setStatus(OrderStatus.valueOf(orderRequest.getStatus()));

            Optional<User> optionalUser = userRepository.findById(orderRequest.getUserId());
            if (optionalUser.isPresent()) {
                order.setUser(optionalUser.get());
            } else {
                throw new NoSuchElementException("User with id: " + orderRequest.getUserId() + " not found.");
            }

            order.setAddress(orderRequest.getAddress());
            orderRepository.save(order);
            return orderMapper.toResponse(order);
        } else {
            throw new NoSuchElementException("Order with id: " + orderId + " not found.");
        }
    }
}