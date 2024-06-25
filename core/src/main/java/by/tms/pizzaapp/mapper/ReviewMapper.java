package by.tms.pizzaapp.mapper;

import by.tms.pizzaapp.dto.review.ReviewRequest;
import by.tms.pizzaapp.dto.review.ReviewResponse;
import by.tms.pizzaapp.entity.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequest reviewRequest);

    @Mapping(source = "pizza.id", target = "pizzaId")
    ReviewResponse toResponse(Review review);
}