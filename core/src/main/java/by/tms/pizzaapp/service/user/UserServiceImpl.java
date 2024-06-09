package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.UserRegistrationRequest;
import by.tms.pizzaapp.dto.user.UserResponse;
import by.tms.pizzaapp.entity.user.User;
import by.tms.pizzaapp.entity.user.UserRole;
import by.tms.pizzaapp.mapper.UserRegistrationMapper;
import by.tms.pizzaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegistrationMapper userRegistrationMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRegistrationMapper userRegistrationMapper) {
        this.userRepository = userRepository;
        this.userRegistrationMapper = userRegistrationMapper;
    }

    @Override
    public UserResponse registerUser(UserRegistrationRequest userRequest) {
        User user = userRegistrationMapper.toEntity(userRequest);

        if (userRepository.count() == 0) {
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.CLIENT);
        }

        User savedUser = userRepository.save(user);
        return userRegistrationMapper.toResponse(savedUser);
    }
}