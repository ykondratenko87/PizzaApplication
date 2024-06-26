package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.service.basket.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/baskets")
@Tag(name = "Basket Controller")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @Operation(summary = "addPizzaToBasket")
    @PostMapping
    public BasketResponse addPizzaToBasket(@Valid @RequestBody BasketRequest basketRequest) {
        log.info("Add pizza to basket and creating new basket: {}", basketRequest);
        return basketService.addPizzaToBasket(basketRequest);
    }
}