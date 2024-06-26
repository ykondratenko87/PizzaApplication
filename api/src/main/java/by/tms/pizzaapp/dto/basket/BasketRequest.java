package by.tms.pizzaapp.dto.basket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Schema(description = "Basket entity")
@Data
public class BasketRequest {
    @Positive(message = "Count must be positive")
    private long count;
    @Positive(message = "Total price must be positive")
    private double totalPrice;
    @NotEmpty(message = "Pizza IDs cannot be empty")
    private List<Long> pizzaIds;
    @NotEmpty(message = "Order IDs cannot be empty")
    private List<Long> orderIds;
}