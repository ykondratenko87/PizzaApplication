package by.tms.pizzaapp.dto.courier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(description = "Courier request")
@Data
public class CourierRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 10, message = "Login must be between 3 and 10 characters")
    private String login;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    private String password;
    @NotBlank(message = "Role is required")
    private String role = "COURIER";;
}