package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.basket.*;
import by.tms.pizzaapp.entity.basket.Basket;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "pizzas", ignore = true)
    Basket toEntity(BasketRequest basketRequest);

    BasketResponse toResponse(Basket basket);
}