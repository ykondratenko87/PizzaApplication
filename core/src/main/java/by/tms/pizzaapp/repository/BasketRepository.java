package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByOrdersId(Long orderId);
}