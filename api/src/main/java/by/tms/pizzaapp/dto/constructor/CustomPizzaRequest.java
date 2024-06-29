package by.tms.pizzaapp.dto.constructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

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