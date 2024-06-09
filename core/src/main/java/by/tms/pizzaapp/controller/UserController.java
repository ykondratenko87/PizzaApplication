package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.user.UserRegistrationRequest;
import by.tms.pizzaapp.dto.user.UserResponse;
import by.tms.pizzaapp.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Some admin's functions")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "registerUser")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody UserRegistrationRequest userRequest) {
        log.info("Registering new user with data: {}", userRequest);
        return userService.registerUser(userRequest);
    }
}