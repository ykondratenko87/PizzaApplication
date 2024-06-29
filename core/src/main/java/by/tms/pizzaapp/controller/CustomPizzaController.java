package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.constructor.CustomPizzaRequest;
import by.tms.pizzaapp.dto.constructor.CustomPizzaResponse;
import by.tms.pizzaapp.service.constructor.CustomPizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/custom-pizzas")
@Tag(name = "Custom Pizza Controller", description = "Endpoints for managing custom pizzas")
@RequiredArgsConstructor
public class CustomPizzaController {
    private final CustomPizzaService customPizzaService;

    @Operation(summary = "Add ingredient to customPizza", description = "Добавляет ингредиент")
    @PostMapping
    public CustomPizzaResponse addIngredientToCustomPizza(@Valid @org.springframework.web.bind.annotation.RequestBody CustomPizzaRequest customPizzaRequest) {
        log.info("Add ingredient to customPizza: {}", customPizzaRequest);
        return customPizzaService.addIngredientToCustomPizza(customPizzaRequest);
    }
}