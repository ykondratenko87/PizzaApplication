package by.tms.pizzaapp.dto.review;

import lombok.Data;

@Data
public class ReviewResponse {
    private long id;
    private String description;
    private long pizzaId;
}