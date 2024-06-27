package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.order.OrderRequest;
import by.tms.pizzaapp.dto.order.OrderResponse;
import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.pizza.Pizza;
import by.tms.pizzaapp.repository.PizzaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "userId", target = "user.id")
    Order toEntity(OrderRequest orderRequest);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pizzas", target = "pizzas")  // Добавьте это отображение
    OrderResponse toResponse(Order order);

    List<PizzaResponse> pizzasToPizzaResponses(List<Pizza> pizzas);  // Добавьте это
}