package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.constructor.CustomPizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomPizzaRepository extends JpaRepository<CustomPizza, Long> {
    Optional<CustomPizza> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}