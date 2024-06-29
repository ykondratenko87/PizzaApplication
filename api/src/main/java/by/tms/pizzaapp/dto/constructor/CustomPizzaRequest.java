package by.tms.pizzaapp.dto.constructor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "CustomPizza request")
@Data
public class CustomPizzaRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long ingredientId;
    @Positive(message = "Count must be positive")
    private long count;
    @Positive(message = "Total price must be positive")
    private double totalSum;
}