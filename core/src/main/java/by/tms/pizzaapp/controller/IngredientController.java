package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.ingredient.IngredientRequest;
import by.tms.pizzaapp.dto.ingredient.IngredientResponse;
import by.tms.pizzaapp.service.ingredient.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ingredient Controller")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @Operation(summary = "Get all ingredients")
    @GetMapping
    public List<IngredientResponse> getAllIngredients() {
        log.info("Fetching all ingredients");
        return ingredientService.getAllIngredients();
    }

    @Operation(summary = "Create new ingredient")
    @PostMapping
    public IngredientResponse createIngredient(@Valid @RequestBody IngredientRequest ingredientRequest) {
        log.info("Creating new ingredient: {}", ingredientRequest);
        return ingredientService.createIngredient(ingredientRequest);
    }
}