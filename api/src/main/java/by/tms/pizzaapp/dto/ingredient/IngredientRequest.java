package by.tms.pizzaapp.dto.ingredient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IngredientRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive(message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Quantity must be non-negative")
    private int portion;
}