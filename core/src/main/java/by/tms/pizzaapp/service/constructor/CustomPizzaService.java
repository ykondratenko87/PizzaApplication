package by.tms.pizzaapp.service.constructor;

import by.tms.pizzaapp.dto.basket.*;
import by.tms.pizzaapp.dto.constructor.*;

public interface CustomPizzaService {
    CustomPizzaResponse addIngredientToCustomPizza(CustomPizzaRequest customPizzaRequest);

    CustomPizzaResponse removeIngredientFromCustomPizza(Long customPizzaId, Long ingredientId);

    BasketResponse addCustomPizzaToBasket(Long customPizzaId, BasketRequest basketRequest);
}