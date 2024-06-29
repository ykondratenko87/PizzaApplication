package by.tms.pizzaapp.service.review;

import by.tms.pizzaapp.dto.review.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewResponse> getAllReviews();

    Optional<ReviewResponse> getReviewById(long id);

    ReviewResponse createReview(ReviewRequest reviewRequest);

    boolean deleteReviewById(long id);

    Optional<ReviewResponse> updateReview(long id, ReviewRequest reviewRequest);

    List<ReviewResponse> getReviewByPizzaName(String pizzaName);

    Page<ReviewResponse> getAllReviewsWithPagination(Pageable pageable);
}