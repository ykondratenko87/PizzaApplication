package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.basket.BasketRequest;
import by.tms.pizzaapp.dto.basket.BasketResponse;
import by.tms.pizzaapp.entity.basket.Basket;
import by.tms.pizzaapp.entity.order.Order;
import by.tms.pizzaapp.entity.pizza.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    @Mapping(target = "pizzas", source = "pizzaIds", qualifiedByName = "mapIdsToPizzas")
    @Mapping(target = "orders", source = "orderIds", qualifiedByName = "mapIdsToOrders")
    Basket toEntity(BasketRequest basketRequest);

    @Mapping(target = "pizzaIds", source = "pizzas", qualifiedByName = "mapPizzasToIds")
    @Mapping(target = "orderIds", source = "orders", qualifiedByName = "mapOrdersToIds")
    BasketResponse toResponse(Basket basket);

    @Named("mapIdsToPizzas")
    default List<Pizza> mapIdsToPizzas(List<Long> ids) {
        return ids.stream().map(id -> {
            Pizza pizza = new Pizza();
            pizza.setId(id);
            return pizza;
        }).collect(Collectors.toList());
    }

    @Named("mapIdsToOrders")
    default List<Order> mapIdsToOrders(List<Long> ids) {
        return ids.stream().map(id -> {
            Order order = new Order();
            order.setId(id);
            return order;
        }).collect(Collectors.toList());
    }

    @Named("mapPizzasToIds")
    default List<Long> mapPizzasToIds(List<Pizza> pizzas) {
        return pizzas.stream().map(Pizza::getId).collect(Collectors.toList());
    }

    @Named("mapOrdersToIds")
    default List<Long> mapOrdersToIds(List<Order> orders) {
        return orders.stream().map(Order::getId).collect(Collectors.toList());
    }
}