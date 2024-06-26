package by.tms.pizzaapp.dto.order;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {
    private Long id;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;
    private Long userId;
    private String address;
}