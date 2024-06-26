package by.tms.pizzaapp.service.basket;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;

import java.util.List;
import java.util.Optional;

public interface BasketService {
    List<BasketResponse> getAllBaskets();

    Optional<BasketResponse> getBasketById(long id);

    BasketResponse createBasket(BasketRequest basketRequest);

    void clearBasket(Long basketId);
}