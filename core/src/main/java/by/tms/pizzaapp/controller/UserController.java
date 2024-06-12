package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.user.UserRegistrationRequest;
import by.tms.pizzaapp.dto.user.UserResponse;
import by.tms.pizzaapp.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

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

    @Operation(summary = "getAllUsers")
    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    @Operation(summary = "getAllUsers with pagination")
    @GetMapping("/paged")
    public Page<UserResponse> getAllUsersWithPagination(Pageable pageable) {
        log.info("Fetching all users with pagination");
        return userService.getAllUsersWithPagination(pageable);
    }
}