package by.tms.pizzaapp.dto.basket;

import lombok.Data;

@Data
public class BasketResponse {
    private long id;
    private long count;
    private double totalPrice;
}