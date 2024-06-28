package by.tms.pizzaapp.service.ingredient;

import by.tms.pizzaapp.dto.ingredient.IngredientRequest;
import by.tms.pizzaapp.dto.ingredient.IngredientResponse;

import java.util.List;

public interface IngredientService {
    List<IngredientResponse> getAllIngredients();
    IngredientResponse createIngredient(IngredientRequest ingredientRequest);
}