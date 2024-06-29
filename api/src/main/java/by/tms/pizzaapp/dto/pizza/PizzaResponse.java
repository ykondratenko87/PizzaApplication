package by.tms.pizzaapp.dto.pizza;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Pizza response")
@Data
public class PizzaResponse {
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}