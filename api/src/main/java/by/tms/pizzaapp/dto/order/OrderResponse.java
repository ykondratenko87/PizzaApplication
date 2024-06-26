package by.tms.pizzaapp.dto.order;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {
    private long id;
    private double pizzaPrice;
    private LocalDate orderDate;
    private String status;
    private long userId;
    private String address;
}