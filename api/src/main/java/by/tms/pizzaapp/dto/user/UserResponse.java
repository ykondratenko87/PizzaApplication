package by.tms.pizzaapp.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User response")
@Data
public class UserResponse {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String role;
}