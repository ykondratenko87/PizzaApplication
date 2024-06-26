package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    Basket toEntity(BasketRequest basketRequest);

    BasketResponse toResponse(Basket basket);
}