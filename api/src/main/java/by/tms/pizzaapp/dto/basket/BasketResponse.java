package by.tms.pizzaapp.dto.basket;

import lombok.Data;

import java.util.List;

@Data
public class BasketResponse {
    private long id;
    private long count;
    private double totalPrice;
    private List<Long> pizzaIds;
    private List<Long> orderIds;
}