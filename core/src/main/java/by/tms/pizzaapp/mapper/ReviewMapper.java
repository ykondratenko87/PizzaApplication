package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.review.*;
import by.tms.pizzaapp.entity.review.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequest reviewRequest);

    @Mapping(source = "pizza.id", target = "pizzaId")
    ReviewResponse toResponse(Review review);
}