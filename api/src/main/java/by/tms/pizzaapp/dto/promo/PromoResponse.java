package by.tms.pizzaapp.dto.promo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Promo response")
@Data
public class PromoResponse {
    private long id;
    private String name;
}