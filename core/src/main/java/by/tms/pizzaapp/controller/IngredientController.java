package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.ingredient.*;
import by.tms.pizzaapp.service.ingredient.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ingredient Controller", description = "Endpoints for managing ingredient controller")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @Operation(summary = "Get all ingredients", description = "Получить все ингредиенты")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResponse> getAllIngredients() {
        log.info("Fetching all ingredients");
        return ingredientService.getAllIngredients();
    }

    @Operation(summary = "Create new ingredient", description = "Добавить новый ингредиент")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse createIngredient(@Valid @RequestBody IngredientRequest ingredientRequest) {
        log.info("Creating new ingredient: {}", ingredientRequest);
        return ingredientService.createIngredient(ingredientRequest);
    }

    @Operation(summary = "Delete ingredient by ID", description = "Удалить ингредиент")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredientById(@PathVariable Long id) {
        log.info("Deleting ingredient with id: {}", id);
        ingredientService.deleteIngredientById(id);
    }

    @Operation(summary = "Update ingredient by ID", description = "Обновить ингредиент")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientResponse updateIngredientById(@PathVariable Long id, @Valid @RequestBody IngredientRequest ingredientRequest) {
        log.info("Updating ingredient with id: {}", id);
        return ingredientService.updateIngredientById(id, ingredientRequest);
    }
}