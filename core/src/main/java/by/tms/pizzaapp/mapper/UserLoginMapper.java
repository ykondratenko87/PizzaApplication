package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.user.*;
import by.tms.pizzaapp.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLoginMapper {
    User toEntity(UserLoginRequest userLoginRequest);

    UserResponse toResponse(User user);
}