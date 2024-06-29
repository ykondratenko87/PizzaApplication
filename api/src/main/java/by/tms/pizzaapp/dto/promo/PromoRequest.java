package by.tms.pizzaapp.dto.promo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Promo request")
@Data
public class PromoRequest {
    @NotBlank(message = "Name is required")
    private String name;
}