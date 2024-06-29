package by.tms.pizzaapp.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Review response")
@Data
public class ReviewResponse {
    private long id;
    private String description;
    private long pizzaId;
}