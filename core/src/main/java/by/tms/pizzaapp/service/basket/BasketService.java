package by.tms.pizzaapp.service.basket;

import by.tms.pizzaapp.dto.basket.*;

public interface BasketService {
    BasketResponse addPizzaToBasket(BasketRequest basketRequest);

    BasketResponse removePizzaFromBasket(Long basketId, Long pizzaId);

    BasketResponse getBasketByUserId(Long userId);

    BasketResponse applyPromoCode(Long basketId, String promoCode);
}