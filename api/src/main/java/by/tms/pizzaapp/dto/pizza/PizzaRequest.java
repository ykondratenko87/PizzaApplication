package by.tms.pizzaapp.dto.pizza;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "Pizza request")
@Data
public class PizzaRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @Positive(message = "Quantity must be positive")
    private int quantity;
}