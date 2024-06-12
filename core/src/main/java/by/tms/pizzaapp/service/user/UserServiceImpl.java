package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.*;
import by.tms.pizzaapp.entity.user.*;
import by.tms.pizzaapp.mapper.UserRegistrationMapper;
import by.tms.pizzaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
//    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userRegistrationMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> getAllUsersWithPagination(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return new PageImpl<>(
                userPage.getContent().stream()
                        .map(userRegistrationMapper::toResponse)
                        .collect(Collectors.toList()),
                pageable,
                userPage.getTotalElements()
        );
    }
}