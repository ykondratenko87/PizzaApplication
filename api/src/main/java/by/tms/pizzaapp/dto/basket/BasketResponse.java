package by.tms.pizzaapp.dto.basket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Basket response")
@Data
public class BasketResponse {
    private long id;
    private long count;
    private double totalPrice;
}