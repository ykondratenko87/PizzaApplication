package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}