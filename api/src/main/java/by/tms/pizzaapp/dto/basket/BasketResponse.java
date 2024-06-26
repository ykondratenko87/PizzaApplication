package by.tms.pizzaapp.dto.basket;

import lombok.Data;

@Data
public class BasketResponse {
    private long id;
    private String pizzaName;
    private long count;
    private double totalPrice;
}