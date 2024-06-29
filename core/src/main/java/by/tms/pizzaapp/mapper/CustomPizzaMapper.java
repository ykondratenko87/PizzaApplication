package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.constructor.CustomPizzaRequest;
import by.tms.pizzaapp.dto.constructor.CustomPizzaResponse;
import by.tms.pizzaapp.entity.constructor.CustomPizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface CustomPizzaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "ingredients", ignore = true)
    CustomPizza toEntity(CustomPizzaRequest CustomPizzaRequest);

    CustomPizzaResponse toResponse(CustomPizza customPizza);
}