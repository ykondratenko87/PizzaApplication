package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.UserRegistrationRequest;
import by.tms.pizzaapp.dto.user.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRequest);
}