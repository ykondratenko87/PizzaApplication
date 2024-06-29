package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.ingredient.IngredientRequest;
import by.tms.pizzaapp.dto.ingredient.IngredientResponse;
import by.tms.pizzaapp.entity.ingredient.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient toEntity(IngredientRequest ingredientRequest);

    IngredientResponse toResponse(Ingredient ingredient);
}