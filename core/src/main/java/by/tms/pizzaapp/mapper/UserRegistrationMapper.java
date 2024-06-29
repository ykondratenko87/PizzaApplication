package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.user.*;
import by.tms.pizzaapp.entity.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRegistrationRequest userRegistrationRequest);

    UserResponse toResponse(User user);
}