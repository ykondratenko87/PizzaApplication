package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.order.*;
import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.pizza.Pizza;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderRequest orderRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pizzas", target = "pizzas")
    OrderResponse toResponse(Order order);

    List<PizzaResponse> pizzasToPizzaResponses(List<Pizza> pizzas);
}