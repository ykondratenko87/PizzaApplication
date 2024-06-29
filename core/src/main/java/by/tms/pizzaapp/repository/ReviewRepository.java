package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.entity.review.Review;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPizza(Pizza pizza);

    Page<Review> findAll(Pageable pageable);
}