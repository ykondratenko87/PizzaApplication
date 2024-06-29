package by.tms.pizzaapp.dto.order;

import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Order response")
@Data
public class OrderResponse {
    private Long id;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;
    private Long userId;
    private String address;
    private List<PizzaResponse> pizzas;
}