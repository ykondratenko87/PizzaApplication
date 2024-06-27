package by.tms.pizzaapp.service.basket;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;

public interface BasketService {
    BasketResponse addPizzaToBasket(BasketRequest basketRequest);
    BasketResponse removePizzaFromBasket(Long basketId, Long pizzaId);
    BasketResponse getBasketByUserId(Long userId);
}