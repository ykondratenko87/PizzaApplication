package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.entity.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "user.id", source = "userId")
    Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "userId", source = "user.id")
    OrderResponse toResponse(Order order);
}