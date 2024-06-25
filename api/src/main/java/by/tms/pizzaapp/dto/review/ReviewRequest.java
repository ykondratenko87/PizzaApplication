package by.tms.pizzaapp.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Review entity")
@Data
public class ReviewRequest {
    @NotNull(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;
    @NotNull(message = "Pizza ID is required")
    private long pizzaId;
}