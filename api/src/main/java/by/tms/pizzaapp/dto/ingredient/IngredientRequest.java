package by.tms.pizzaapp.dto.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "Ingredient request")
@Data
public class IngredientRequest {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive(message = "Price must be positive")
    private double price;
    @Min(value = 0, message = "Quantity must be non-negative")
    private int portion;
}