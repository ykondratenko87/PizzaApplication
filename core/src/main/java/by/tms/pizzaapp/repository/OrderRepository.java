package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}