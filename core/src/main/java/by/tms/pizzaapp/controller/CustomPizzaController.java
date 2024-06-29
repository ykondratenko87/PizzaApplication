package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.basket.*;
import by.tms.pizzaapp.dto.constructor.*;
import by.tms.pizzaapp.service.constructor.CustomPizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/custom-pizzas")
@Tag(name = "Custom Pizza Controller", description = "Endpoints for managing custom pizza controller")
@RequiredArgsConstructor
public class CustomPizzaController {
    private final CustomPizzaService customPizzaService;

    @Operation(summary = "Add ingredient to customPizza", description = "Добавляет ингредиент")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomPizzaResponse addIngredientToCustomPizza(@Valid @org.springframework.web.bind.annotation.RequestBody CustomPizzaRequest customPizzaRequest) {
        log.info("Add ingredient to customPizza: {}", customPizzaRequest);
        return customPizzaService.addIngredientToCustomPizza(customPizzaRequest);
    }

    @Operation(summary = "Remove ingredient from customPizza", description = "Удаляет ингредиент")
    @DeleteMapping("/{customPizzaId}/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomPizzaResponse> removeIngredientFromCustomPizza(@PathVariable Long customPizzaId, @PathVariable Long ingredientId) {
        log.info("Remove ingredient from customPizza: customPizzaId={}, ingredientId={}", customPizzaId, ingredientId);
        return ResponseEntity.ok(customPizzaService.removeIngredientFromCustomPizza(customPizzaId, ingredientId));
    }

    @Operation(summary = "Add custom pizza to basket", description = "Добавляет пользовательскую пиццу в корзину")
    @PostMapping("/{customPizzaId}/basket")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BasketResponse> addCustomPizzaToBasket(@PathVariable Long customPizzaId, @RequestBody @Valid BasketRequest basketRequest) {
        log.info("Add custom pizza to basket: customPizzaId={}, basketRequest={}", customPizzaId, basketRequest);
        BasketResponse basketResponse = customPizzaService.addCustomPizzaToBasket(customPizzaId, basketRequest);
        return ResponseEntity.ok(basketResponse);
    }
}