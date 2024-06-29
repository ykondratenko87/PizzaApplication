package by.tms.pizzaapp.dto.basket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "Basket request")
@Data
public class BasketRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long pizzaId;
    @Positive(message = "Count must be positive")
    private long count;
    @Positive(message = "Total price must be positive")
    private double totalPrice;
}