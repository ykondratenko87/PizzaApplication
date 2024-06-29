package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.courier.CourierRequest;
import by.tms.pizzaapp.entity.courier.Courier;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourierMapper {
    @Mapping(target = "id", ignore = true)
    Courier toEntity(CourierRequest courierRequest);
}