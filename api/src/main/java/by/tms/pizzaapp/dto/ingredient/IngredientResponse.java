package by.tms.pizzaapp.dto.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Ingredient response")
@Data
public class IngredientResponse {
    private Long id;
    private String name;
    private double price;
    private int portion;
}