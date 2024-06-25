package by.tms.pizzaapp.dto.pizza;

import lombok.Data;

@Data
public class PizzaResponse {
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}