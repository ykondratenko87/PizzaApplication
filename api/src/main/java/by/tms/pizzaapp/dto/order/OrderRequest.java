package by.tms.pizzaapp.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Order request")
@Data
public class OrderRequest {
    @Positive(message = "Pizza price must be positive")
    private double totalPrice;
    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;
    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "ORDERING|COMPLETED", message = "Status must be one of ORDERING, or COMPLETED")
    private String status;
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be positive")
    private Long userId;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotNull(message = "Pizza IDs cannot be null")
    private List<Long> pizzaIds;
}