package by.tms.pizzaapp.dto.ingredient;

import lombok.Data;

@Data
public class IngredientResponse {
    private Long id;
    private String name;
    private double price;

    private int portion;
}