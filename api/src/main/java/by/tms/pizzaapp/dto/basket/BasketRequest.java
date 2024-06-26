package by.tms.pizzaapp.dto.basket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Schema(description = "Basket entity")
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