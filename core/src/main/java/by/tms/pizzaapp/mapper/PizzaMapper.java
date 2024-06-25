package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.pizza.PizzaRequest;
import by.tms.pizzaapp.dto.pizza.PizzaResponse;
import by.tms.pizzaapp.entity.pizza.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    Pizza toEntity(PizzaRequest pizzaRequest);

    PizzaResponse toResponse(Pizza pizza);
}