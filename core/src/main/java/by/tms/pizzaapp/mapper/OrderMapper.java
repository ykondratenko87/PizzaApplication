package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.entity.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderRequest orderRequest);

    @Mapping(source = "user.id", target = "userId")
    OrderResponse toResponse(Order order);
}