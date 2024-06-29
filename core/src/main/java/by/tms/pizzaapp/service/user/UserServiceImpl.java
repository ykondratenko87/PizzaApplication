package by.tms.pizzaapp.service.user;

import by.tms.pizzaapp.dto.user.*;
import by.tms.pizzaapp.entity.user.*;
import by.tms.pizzaapp.mapper.UserRegistrationMapper;
import by.tms.pizzaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegistrationMapper userRegistrationMapper;

    @Override
    public UserResponse registerUser(UserRegistrationRequest userRequest) {
        Optional<User> existingUser = userRepository.findByLogin(userRequest.getLogin());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this login already exists");
        }
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
    public UserResponse loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(userLoginRequest.getLogin());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userLoginRequest.getPassword().equals(user.getPassword())) {
                return userRegistrationMapper.toResponse(user);
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
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

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userRegistrationMapper.toResponse(user);
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public UserResponse updateUser(Long id, UserRegistrationRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setName(userRequest.getName());
        existingUser.setSurname(userRequest.getSurname());
        existingUser.setLogin(userRequest.getLogin());
        existingUser.setPassword(userRequest.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return userRegistrationMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponse getUserByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userRegistrationMapper.toResponse(user);
    }
}