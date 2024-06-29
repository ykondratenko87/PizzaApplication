package by.tms.pizzaapp.service.constructor;

import by.tms.pizzaapp.dto.constructor.CustomPizzaRequest;
import by.tms.pizzaapp.dto.constructor.CustomPizzaResponse;

public interface CustomPizzaService {
    CustomPizzaResponse addIngredientToCustomPizza(CustomPizzaRequest customPizzaRequest);
    CustomPizzaResponse removeIngredientFromCustomPizza(Long customPizzaId, Long ingredientId);
}