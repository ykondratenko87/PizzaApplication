package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.pizza.Pizza;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Optional<Pizza> findByName(String name);

    Optional<Pizza> findByNameAndDescriptionAndPrice(String name, String description, double price);

    Page<Pizza> findAll(Pageable pageable);
}