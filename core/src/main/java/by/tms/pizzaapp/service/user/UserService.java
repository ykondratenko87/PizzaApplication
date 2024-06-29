package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.*;
import org.springframework.data.domain.*;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest userRequest);

    UserResponse loginUser(UserLoginRequest userLoginRequest);

    List<UserResponse> getAllUsers();

    Page<UserResponse> getAllUsersWithPagination(Pageable pageable);

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);

    UserResponse updateUser(Long id, UserRegistrationRequest userRequest);

    UserResponse getUserByLogin(String login);
}