package by.tms.pizzaapp.service.ingredient;

import by.tms.pizzaapp.dto.ingredient.*;

import java.util.List;

public interface IngredientService {
    List<IngredientResponse> getAllIngredients();

    IngredientResponse createIngredient(IngredientRequest ingredientRequest);

    void deleteIngredientById(Long id);

    IngredientResponse updateIngredientById(Long id, IngredientRequest ingredientRequest);
}