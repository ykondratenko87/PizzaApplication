package by.tms.pizzaapp.controller;

import by.tms.pizzaapp.dto.user.*;
import by.tms.pizzaapp.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoints for managing user controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "registerUser", description = "Регистрация пользователя")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody UserRegistrationRequest userRequest) {
        log.info("Registering new user with data: {}", userRequest);
        return userService.registerUser(userRequest);
    }

    @Operation(summary = "loginUser", description = "Авторизация пользователя")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        log.info("Logging in user with login: {}", userLoginRequest.getLogin());
        return userService.loginUser(userLoginRequest);
    }

    @Operation(summary = "getAllUsers", description = "Получить всех пользователей")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    @Operation(summary = "getAllUsers with pagination", description = "Получить всех пользователей с пагинацией")
    @GetMapping("/paged")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponse> getAllUsersWithPagination(Pageable pageable) {
        log.info("Fetching all users with pagination");
        return userService.getAllUsersWithPagination(pageable);
    }

    @Operation(summary = "getUserById", description = "Получить пользователя по ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse getUserById(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        return userService.getUserById(id);
    }

    @Operation(summary = "deleteUserById", description = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUserById(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUserById(id);
    }

    @Operation(summary = "updateUser", description = "Обновить пользователя")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRegistrationRequest userRequest) {
        log.info("Updating user with id: {}", id);
        return userService.updateUser(id, userRequest);
    }

    @Operation(summary = "getUserByLogin", description = "Получить пользователя по Логину")
    @GetMapping("/login/{login}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse getUserByLogin(@PathVariable String login) {
        log.info("Fetching user by login: {}", login);
        return userService.getUserByLogin(login);
    }
}