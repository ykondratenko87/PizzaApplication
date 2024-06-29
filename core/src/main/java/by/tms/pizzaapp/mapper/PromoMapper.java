package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.promo.*;
import by.tms.pizzaapp.entity.promo.Promo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromoMapper {
    Promo toEntity(PromoRequest promoRequest);

    PromoResponse toResponse(Promo promo);
}