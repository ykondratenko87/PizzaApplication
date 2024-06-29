package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    void deleteById(Long id);

    Optional<Ingredient> findByNameAndPrice(String name, double price);
}