package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserId(Long userId);
}