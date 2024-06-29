package by.tms.pizzaapp.dto.constructor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "CustomPizza response")
@Data
public class CustomPizzaResponse {
    private long id;
    private long count;
    private double totalSum;
}