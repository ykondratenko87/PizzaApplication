package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.UserRegistrationRequest;
import by.tms.pizzaapp.dto.user.UserResponse;
import org.springframework.data.domain.*;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest userRequest);

    List<UserResponse> getAllUsers();

    Page<UserResponse> getAllUsersWithPagination(Pageable pageable);
}