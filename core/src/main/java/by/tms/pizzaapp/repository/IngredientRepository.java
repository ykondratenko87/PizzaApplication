package by.tms.pizzaapp.repository;

import by.tms.pizzaapp.entity.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}