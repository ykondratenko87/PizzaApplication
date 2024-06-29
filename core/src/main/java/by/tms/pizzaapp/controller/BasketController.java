package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.basket.*;
import by.tms.pizzaapp.service.basket.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/baskets")
@Tag(name = "Basket Controller", description = "Endpoints for managing basket controller")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @Operation(summary = "Add pizza to basket", description = "Добавляет пиццу в корзину")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BasketResponse addPizzaToBasket(@Valid @RequestBody BasketRequest basketRequest) {
        log.info("Add pizza to basket: {}", basketRequest);
        return basketService.addPizzaToBasket(basketRequest);
    }

    @Operation(summary = "Remove pizza from basket", description = "Удаляет пиццу из корзины")
    @DeleteMapping("/{basketId}/pizzas/{pizzaId}")
    @ResponseStatus(HttpStatus.OK)
    public BasketResponse removePizzaFromBasket(@PathVariable Long basketId, @PathVariable Long pizzaId) {
        log.info("Remove pizza from basket: basketId={}, pizzaId={}", basketId, pizzaId);
        return basketService.removePizzaFromBasket(basketId, pizzaId);
    }

    @Operation(summary = "Get basket by user ID", description = "Получает корзину по ID пользователя")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public BasketResponse getBasketByUserId(@PathVariable Long userId) {
        log.info("Get basket by user ID: {}", userId);
        return basketService.getBasketByUserId(userId);
    }

    @Operation(summary = "Apply promo code to basket", description = "Применяет промокод к корзине")
    @PostMapping("/{basketId}/apply-promo")
    @ResponseStatus(HttpStatus.OK)
    public BasketResponse applyPromoCode(@PathVariable Long basketId, @RequestParam String promoCode) {
        log.info("Apply promo code: {} to basket: {}", promoCode, basketId);
        return basketService.applyPromoCode(basketId, promoCode);
    }
}