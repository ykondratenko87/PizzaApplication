package by.tms.pizzaapp.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String role;
}